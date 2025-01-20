package in.xlendz.service;

import in.xlendz.components.LenderDetailsFacade;
import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.LenderDetailsType;
import in.xlendz.entity.*;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.responses.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class LenderDetailsServiceImpl implements LenderDetailsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LenderDetailsServiceImpl.class);
    private final LenderDetailsFacade lenderDetailsFacade;
    private final ModelMapper modelMapper;

    @Autowired
    public LenderDetailsServiceImpl(LenderDetailsFacade lenderDetailsFacade, ModelMapper modelMapper) {
        this.lenderDetailsFacade = lenderDetailsFacade;
        this.modelMapper = modelMapper;
    }

    @Override
    public XlendzLender getLenderByEmail(String lenderEmail) {
        LOGGER.info("Received request to get lender by email, lenderEmail={}", lenderEmail);
        Optional<XlendzLender> lender = lenderDetailsFacade.getLenderByEmail(lenderEmail);
        if (lender.isPresent()) {
            LOGGER.info("Lender found with email={}", lenderEmail);
            return lender.get();
        }
        throw new UserNotFoundException(ExceptionConstants.LENDER_NOT_FOUND + lenderEmail);
    }

    @Override
    public LenderDetailsDTO getLenderDetails(String lenderEmail) {
        try {
            LOGGER.info("Getting lender details, lenderEmail={}", lenderEmail);

            XlendzLender lender = getLenderByEmail(lenderEmail);
            LenderDetailsDTO.LenderDetailsDTOBuilder lenderDetailsDTO = LenderDetailsDTO.builder();
            lenderDetailsDTO.isVerified(lender.getIsVerified());
            lenderDetailsDTO.kycStatus(lender.getKycStatus());

            if (populateDetails(lenderDetailsDTO,
                    LenderDetailsType.BASIC_DETAILS,
                    lenderDetailsFacade.getLenderBasicDetails(lender),
                    LenderBasicDetailsDTO.class)) {
                return lenderDetailsDTO.build();
            }

            if (populateListTypeDetails(lenderDetailsDTO, LenderDetailsType.DOCUMENTS,
                    lenderDetailsFacade.getLenderDocuments(lender))) {
                return lenderDetailsDTO.build();
            }

            if (populateListTypeDetails(lenderDetailsDTO,
                    LenderDetailsType.SIGNATORIES,
                    lenderDetailsFacade.getLendorSignatories(lender)
            )) {
                return lenderDetailsDTO.build();
            }

            if (populateListTypeDetails(lenderDetailsDTO,
                    LenderDetailsType.KEY_PERSONS,
                    lenderDetailsFacade.getLenderKeyPersons(lender)
            )) {
                return lenderDetailsDTO.build();
            }

            if (populateDetails(lenderDetailsDTO,
                    LenderDetailsType.COMPANY_PROFILE,
                    lenderDetailsFacade.getLenderProfile(lender),
                    LenderProfileDTO.class)) {
                return lenderDetailsDTO.build();
            }

            if (populateDetails(lenderDetailsDTO,
                    LenderDetailsType.TAX_INFO,
                    lenderDetailsFacade.getLenderTaxInfo(lender),
                    LenderTaxInfoDTO.class)) {
                return lenderDetailsDTO.build();
            }

            if (populateListTypeDetails(lenderDetailsDTO,
                    LenderDetailsType.SECURITY_COMPLIANCE_DOCS,
                    lenderDetailsFacade.getLenderSecurityDocs(lender))) {
                return lenderDetailsDTO.build();
            }

            if (populateDetails(lenderDetailsDTO,
                    LenderDetailsType.BANK_DETAILS,
                    lenderDetailsFacade.getLenderBankDetails(lender),
                    LenderBankDetailsDTO.class)) {
                return lenderDetailsDTO.build();
            }

            return lenderDetailsDTO.build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Failed to retrieve lender details for email: " + lenderEmail, e);
        }
    }

    private <T> boolean populateListTypeDetails(LenderDetailsDTO.LenderDetailsDTOBuilder lenderDetailsDTO,
                                                LenderDetailsType detailName,
                                                Optional<List<T>> listDetails) {
        if (listDetails.isEmpty() || listDetails.get().isEmpty()) {
            LOGGER.info("{} not found, skipping.", detailName);
            return true;
        }

        LOGGER.info("{} found, mapping to DTO.", detailName);

        try {
            switch (detailName) {
                case DOCUMENTS -> mapAndSetList(listDetails.get(),
                        LenderDocumentsDTO.class,
                        lenderDetailsDTO::lenderDocumentsDTO);
                case SIGNATORIES -> mapAndSetList(listDetails.get(),
                        LenderSignatoriesDTO.class,
                        lenderDetailsDTO::lenderSignatoriesDTO);
                case KEY_PERSONS -> mapAndSetList(listDetails.get(),
                        LenderKeyPersonsDTO.class,
                        lenderDetailsDTO::lenderKeyPersons);
                case SECURITY_COMPLIANCE_DOCS -> mapAndSetList(listDetails.get(),
                        LenderSecurityComplianceDocsDTO.class,
                        lenderDetailsDTO::lenderSecurityComplianceDocs);
                default -> {
                    LOGGER.error("Unexpected detail name: {}", detailName);
                    throw new IllegalArgumentException("Unexpected detail name: " + detailName);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error mapping {} details: {}", detailName, e.getMessage());
            throw new IllegalStateException("Error mapping " + detailName + " details", e);
        }
        return false;
    }

    private <D> void mapAndSetList(List<?> sourceList, Class<D> dtoClass, Consumer<List<D>> setter) {
        List<D> dtoList = sourceList.stream()
                .map(item -> modelMapper.map(item, dtoClass))
                .toList();
        setter.accept(dtoList);
    }


    private <T, D> boolean populateDetails(
            LenderDetailsDTO.LenderDetailsDTOBuilder builder,
            LenderDetailsType detailName,
            Optional<T> optionalDetail,
            Class<D> dtoClass) {
        if (optionalDetail.isEmpty()) {
            LOGGER.info("{} not found, skipping.", detailName);
            return true;
        }

        LOGGER.info("{} found, mapping to DTO.", detailName);
        D dto = modelMapper.map(optionalDetail.get(), dtoClass);

        switch (detailName) {
            case BASIC_DETAILS -> builder.lenderBasicDetailsDTO((LenderBasicDetailsDTO) dto);
            case COMPANY_PROFILE -> builder.lenderProfile((LenderProfileDTO) dto);
            case TAX_INFO -> builder.lenderTaxInfo((LenderTaxInfoDTO) dto);
            case BANK_DETAILS -> builder.lenderBankDetails((LenderBankDetailsDTO) dto);
            default -> {
                LOGGER.error("Unexpected detail name: {}", detailName);
                throw new IllegalArgumentException("Unexpected detail name: " + detailName);
            }
        }
        return false;
    }

}
