package test;

import model.domain.Users;
import org.junit.Test;
import service.RegisterService;
import service.impl.RegisterServiceImpl;

import static org.junit.Assert.*;

public class RegisterServiceImplTest
{
    RegisterService registerService = new RegisterServiceImpl();
    @Test
    public void register()
            throws Exception
    {
        Users user = new Users();
        user.setEmail("2068827836@qq.com");
        registerService.register(user);
    }

    @Test
    public void activeUser()
            throws Exception
    {
    }

}