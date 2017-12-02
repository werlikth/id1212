package id1212.werlinder.marcus.homework3.server.integration;

import id1212.werlinder.marcus.homework3.common.dtoInfo.FileStruct;
import id1212.werlinder.marcus.homework3.server.model.ClientHandler;
import id1212.werlinder.marcus.homework3.server.model.FileDB;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class FileDI {

    /**
     * Retrieve file info from the database
     */
    public FileDB getFileByName(String filename) {
        Session session = FileDB.getSession();

        Query query = session.getNamedQuery("checkFile");
        query.setString(0, filename);

        return (FileDB) query.getSingleResult();
    }

    /**
     * Inserting a new file in the database
     */
    public void insert(ClientHandler client, FileStruct fileDTO) {
        Session session = FileDB.getSession();

        try {
            session.beginTransaction();

            FileDB file = new FileDB();
            file.setOwner(client.getUserDB());
            file.setName(fileDTO.getFilename());
            file.setPublicAccess(fileDTO.isPublicAll());
            file.setSize(fileDTO.getSize());
            file.setReadable(fileDTO.isReadable());
            file.setWritable(fileDTO.isWritable());

            session.save(file);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}