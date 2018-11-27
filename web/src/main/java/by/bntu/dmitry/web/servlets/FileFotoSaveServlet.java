package by.bntu.dmitry.web.servlets;

import by.bntu.dmitry.constants.ConfigConstants;
import by.bntu.dmitry.dao.FotoDAO;
import by.bntu.dmitry.entities.Foto;
import by.bntu.dmitry.entities.User;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author user
 */
@WebServlet("/fileFotoSave")
@MultipartConfig
public class FileFotoSaveServlet extends ManagerServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("fileFotoSaveServlet");
        Part filePart = req.getPart("file");
        String fileType = "";
        boolean isValid = true;
        boolean isTypeFind = false;
        for (int i = filePart.getSubmittedFileName().length() - 1; i >= 0; i--) {
            if (!isTypeFind) {
                fileType = filePart.getSubmittedFileName().charAt(i) + fileType;
            }
            if (filePart.getSubmittedFileName().charAt(i) == '.') {
                break;
            }
        }
        System.out.println("FilePart: " + filePart.getSubmittedFileName());
        User user = (User) req.getSession().getAttribute("user");

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String dir = user.getId() + "/";
        File file = new File(ConfigConstants.IMAGE_FOLDER + dir);
        System.out.println("File: " + file.getAbsolutePath());
        if (fileType.toLowerCase().equals(".jpg")) {
            dir = dir + (FotoDAO.INSTANCE.getAmountByUserId(user)) + ".jpg";
        } else {
            if (fileType.toLowerCase().equals(".png")) {
                dir = dir + (FotoDAO.INSTANCE.getAmountByUserId(user)) + ".png";
            } else {
                if (fileType.toLowerCase().equals(".bmp")) {
                    dir = dir + (FotoDAO.INSTANCE.getAmountByUserId(user)) + ".bmp";
                } else {
                    isValid = false;
                }
            }
        }
        System.out.println("File is valid");
        Map<String, String> map = null;
        if (isValid) {
            InputStream fileContent = filePart.getInputStream();
            System.out.println("68");
            FileOutputStream fos = new FileOutputStream(ConfigConstants.IMAGE_FOLDER + dir);
            byte[] b = new byte[fileContent.available()];
            fileContent.read(b);
            fos.write(b);
            fos.close();
            fileContent.close();
            System.out.println("82");
            Foto foto = new Foto();
            foto.setDirectory(dir);
            foto.setUser((User) req.getSession().getAttribute("user"));
            FotoDAO.INSTANCE.createEntity(foto);
            Foto n_foto = FotoDAO.INSTANCE.getEntityByDirectory(foto.getDirectory());
            map = new HashMap<>();
            map.put("id", n_foto.getId() + "");
            map.put("dir", n_foto.getDirectory());
            map.put("error", "no");
        } else {
            map = new HashMap<>();
            map.put("error", "yes");
        }

        String json = new Gson().toJson(map);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

}