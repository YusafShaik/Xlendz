package in.xlendz.service;

import in.xlendz.entity.XlendzLender;
import in.xlendz.responses.LenderDetailsDTO;

public interface LenderDetailsService {

    XlendzLender getLenderByEmail(String lenderEmail);

    LenderDetailsDTO getLenderDetails(String lenderEmail);
}
