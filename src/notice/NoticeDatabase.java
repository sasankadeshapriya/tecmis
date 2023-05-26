package notice;

import common.code.MyDbConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NoticeDatabase {
    public static List<Notice> getNotices() {
        String query = "SELECT NoticeID, Title, Content, Attachment FROM notice ORDER BY NoticeID DESC";
        
        List<Notice> notices = new ArrayList<>();
        
        try (Connection conn = new MyDbConnector().getMyConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Blob blob = rs.getBlob("attachment");
                InputStream is = blob.getBinaryStream();
                File tempFile = File.createTempFile("attachment", ".pdf");
                FileOutputStream fos = new FileOutputStream(tempFile);

                byte[] buffer = new byte[1024];
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }

                fos.close();

                Notice notice = new Notice(rs.getString("title"), rs.getString("content"), tempFile.toURI().toString());
                notices.add(notice);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return notices;
    }
}

