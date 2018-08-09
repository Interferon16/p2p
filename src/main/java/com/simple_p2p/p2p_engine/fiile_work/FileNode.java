package com.simple_p2p.p2p_engine.fiile_work;

import com.simple_p2p.entity.CommonEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "FileNodes")
public class FileNode implements CommonEntity{

    @Id
/*    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int Id;*/
    @Column(name="filePath")
    private String filePath;

    @Column(name="fileName")
    private String fileName;

    @Column(name="fileNameHash")
    private String fileNameHash;

    @Column(name="fileHash")
    private String fileHash;

    @Column(name="lastModified")
    private Date lastModified;

    public FileNode() {

    }

    public FileNode(String fileName, String fileNameHash, String filePath, String fileHash, Date lastUpdate) {
        this.fileName = fileName;
        this.fileNameHash = fileNameHash;
        this.filePath = filePath;
        this.fileHash = fileHash;
        this.lastModified = lastUpdate;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameHash() {
        return fileNameHash;
    }

    public void setFileNameHash(String fileNameHash) {
        this.fileNameHash = fileNameHash;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileNode fileNode = (FileNode) o;
        return Objects.equals(filePath, fileNode.filePath) &&
                Objects.equals(fileName, fileNode.fileName) &&
                Objects.equals(fileNameHash, fileNode.fileNameHash) &&
                Objects.equals(fileHash, fileNode.fileHash) &&
                Objects.equals(lastModified, fileNode.lastModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, fileName, fileNameHash, fileHash, lastModified);
    }

    @Override
    public String toString() {
        return "FileNode{" +
                "filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileNameHash='" + fileNameHash + '\'' +
                ", fileHash='" + fileHash + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }

    @Override
    public Object getUnique() {
        return filePath;
    }
}
