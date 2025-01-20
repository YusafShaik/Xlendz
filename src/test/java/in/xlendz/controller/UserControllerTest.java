package in.xlendz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.xlendz.service.UserCreationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    @Spy
    private  ObjectMapper objectMapper;
    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        mockMvc= MockMvcBuilders.standaloneSetup(xlendzUserController).build();
    }
    @Mock
    private UserCreationService xlendzUserService;

    @InjectMocks
    UserController xlendzUserController;

  /*  @Test
    void  createLenderTest() throws Exception {
        CreateXlendzUserRequest  request= new CreateXlendzUserRequest("yusafshayk@gmail.com","Yusaf@123");
        String userJson = objectMapper.writeValueAsString(request);
        modelMapper.typeMap(CreateXlendzUserRequest.class, XlendzUser.class).addMappings((maps->
        {
            maps.map(CreateXlendzUserRequest::getEmail, XlendzUser::setEmail);
            maps.map(CreateXlendzUserRequest::getPassword, XlendzUser::setPassword);
        }));
        XlendzUser user=modelMapper.map(request, XlendzUser.class);
        user.setXlendzUserId(1L);
        when(xlendzUserService.createXlendzUser(any(CreateXlendzUserRequest.class))).thenReturn();
        mockMvc.perform(post("/xlendzuser/create").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").value(user.getPassword()));
    }*/

}
