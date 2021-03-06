package com.small.business.service.user;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.small.business.dao.user.UserDao;
import com.small.business.model.user.User;
import com.small.business.model.user.UserPosition;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String SALT = "hr4awesome";

    private static List<User> userList = new ArrayList<User>();

    @Autowired
    UserDao userDao;

    @Value("${passwordRecovery.Url}")
    String passwordRecoveryUrl;

    public List<User> getAllUser() {

        List<User> userList = userDao.getAllUser();
        return userList;
    }
    public List<UserPosition> getAllUserPosition() {
        List<UserPosition> userPositionList = userDao.getAllUserPosition();
        return userPositionList;	
    }
    public User getUserById(Long id) {

        return userDao.getUserById(id);
    }
    public UserPosition getUserPosition(Long userId) {
    	
    	return userDao.getUserPosition(userId);
    }
    public long addUser(User user) {

        return userDao.addUser(user);
    }

    public boolean deleteUserById(Long id) {

        boolean ret;
        ret = userDao.deleteUserById(id);
        return ret;
    }

    public boolean deleteAll() {

        userList.clear();
        return userDao.deleteAll();
    }

    public String uploadFilePostMethod(MultipartFile file,
            HttpServletRequest request) {

        if (file != null) {

            try {
                InputStream inputStream = file.getInputStream();
                if (inputStream == null)
                    LOGGER.debug("File inputstream is null");

                String pathUpDir = request.getSession().getServletContext()
                        .getRealPath("/")
                        + "/resources/cv/";

                File upload = new File(pathUpDir + file.getOriginalFilename());
                file.transferTo(upload);
                String imagePath = pathUpDir + file.getOriginalFilename();
                IOUtils.closeQuietly(inputStream);

            } catch (IOException ex) {
                LOGGER.error("uploadFilePostMethod got error: "
                        + ex.getMessage());
                return "ERROR";
            }
        }

        return "SUCCESS";

    }

    public boolean updateUser(User user) {

        User foundUser = findUserById(user.getId());
        if (foundUser != null) {
            userList.remove(foundUser);
            userList.add(user);
        }
        return userDao.updateUser(user);

    }

    public User findUserById(Long id) {

        return userDao.getUserById(id);
    }

    public Long validateUser(String user, String password) {

        return userDao.validateUser(user, password);
    }

    private String generateForgotPasswordToken(User user) {
        String rawToken = user.getEmail() + System.currentTimeMillis() + SALT;
        return DigestUtils.md5Hex(rawToken);
    }

}
