import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;

public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //получение директории расположения текстового файла
        String filePath = httpServletRequest.getParameter("filePath");
        File file = new File(filePath);

        httpServletResponse.setContentType("text/plain");
        httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + file.getName());

        //скачивание файла, если, конечно, он существует
        if (file.exists())
        {
            try(FileInputStream inputStream = new FileInputStream(file);
                OutputStream outputStream = httpServletResponse.getOutputStream()){
                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try{
            httpServletResponse.setContentType("text/html; charset=UTF-8");

            //извлечение значения из строки ввода
            PrintWriter writer = httpServletResponse.getWriter();
            String key = httpServletRequest.getParameter("word");
            System.out.println(httpServletRequest.getParameter("word"));

            //выполнение запроса к БД
            DatabaseOperations databaseOperations = new DatabaseOperations();
            ArrayList<String> paths = databaseOperations.getPathFiles(key);

            //возвращение ответа клиенту
            if (paths.size() != 0){
                for (String path:paths) {
                    String link = "<p><a href=\"servlet?filePath=" + path + "\">" + path +"</a>";
                    writer.println(link);
                }
            }
            else
                writer.println("По запросу \"" + key + "\" ничего не найдено");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
