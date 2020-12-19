package com.github.gkttk.epam.controller;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.logic.service.impl.UserServiceImpl;
import com.github.gkttk.epam.logic.validator.UserPasswordValidator;
import com.github.gkttk.epam.model.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Optional;
import java.util.UUID;

@WebServlet(urlPatterns = "/upload")
@MultipartConfig(location = "D:\\tmp",
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5) //todo
public class MultipartController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();


private final static String DEFAULT_IMAGE_REF = "static/images/users/not_found.jpg";
    private final static String COMMON_USERS_PREFIX = "static/images/users/";
    private final static String COMMON_SUFFIX = ".jpg";
    private final static String PART_ATTRIBUTE_KEY = "newAvatar";
    private final static String AUTH_USER_ATTRIBUTE_KEY = "authUser";

   private final static String UPLOAD_DIR = "D:\\Projects\\epam\\Cafe\\src\\main\\webapp\\static\\images\\users";//todo absolutepath




    private void redirect(String path, HttpServletResponse response) throws IOException {
        response.sendRedirect(path);
    }

    private String generateFileName(){
        UUID uuid = UUID.randomUUID();
        return (uuid.toString() + COMMON_SUFFIX);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        InputStream partInputStream = null;
        BufferedOutputStream outputStream = null;


        try {
            Part newAvatar = request.getPart(PART_ATTRIBUTE_KEY);

            if(newAvatar == null){
                redirect(getServletContext().getContextPath() + "/controller", response);
            }

            partInputStream = newAvatar.getInputStream();
            byte[] buffer = new byte[partInputStream.available()];
            partInputStream.read(buffer);

            String generatedFileName = generateFileName();



            File file = new File(UPLOAD_DIR + File.separator + generatedFileName);


            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(buffer);


            User authUser = (User) request.getSession().getAttribute(AUTH_USER_ATTRIBUTE_KEY);
            Long userId = authUser.getId();


            String oldImageRef = authUser.getImageRef();
                if(!DEFAULT_IMAGE_REF.equals(oldImageRef)){
                    userService.removeOldImage("D:\\Projects\\epam\\Cafe\\src\\main\\webapp\\" + oldImageRef);//todo
                }

            String newImageRef = COMMON_USERS_PREFIX + generatedFileName;

            userService.changeAvatar(authUser, newImageRef);
            Optional<User> userByIdOpt = userService.getUserById(userId);
            userByIdOpt.ifPresent(user -> request.getSession().setAttribute(AUTH_USER_ATTRIBUTE_KEY, user));




            redirect(getServletContext().getContextPath() + "/controller", response);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        } finally {
            try {
                if (partInputStream != null) {
                    partInputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
