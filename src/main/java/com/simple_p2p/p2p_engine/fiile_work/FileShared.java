package com.simple_p2p.p2p_engine.fiile_work;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.simple_p2p.p2p_engine.enginerepository.DBWriteHandler;
import com.simple_p2p.p2p_engine.enginerepository.FileNodeRepository;
import com.simple_p2p.p2p_engine.server.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;


public class FileShared implements Callable{


    Logger logger = LoggerFactory.getLogger(this.getClass());
    private HashFunction fileBodyHashFunction;
    private HashFunction fileNameHashFunction;
    private ArrayList<FileNode> listOfSharedFiles= new ArrayList<>();
    private long finishHashedSize = 0;
    private long totalHashedSize = 0;
    private ArrayList<File> prepList = new ArrayList<>();
    private long startHashingTime;
    private File target;
    Settings settings;

    public FileShared(File file, Settings settings){
        this.fileNameHashFunction=Hashing.md5();
        this.fileBodyHashFunction=Hashing.sha1();
        this.target=file;
        this.settings=settings;
    }

    @Override
    public ArrayList<FileNode> call() throws Exception {
        return getListOfSharedFiles();
    }

    public ArrayList<FileNode> getListOfSharedFiles() throws FileNotFoundException, AccessDeniedException {
        this.startHashingTime = System.currentTimeMillis();
        prepGatheringList(target);
        hashingFiles(prepList);
        return listOfSharedFiles;
    }

    private void prepGatheringList(File file) throws FileNotFoundException, AccessDeniedException {
        indexShare(file);
        logger.debug("Prepare indexing is finished");
    }


    private void indexShare(File file) throws FileNotFoundException, AccessDeniedException {
        boolean pathExist = file.exists();
        boolean pathReadable = file.canRead();
        boolean pathIsDir = file.isDirectory();
        if(!pathExist){
            throw new FileNotFoundException();
        }
        if(!pathReadable){
            throw new AccessDeniedException(file.getName());
        }
        if(pathIsDir){
            indexDir(file);
        }else{
            prepList.add(file);
            totalHashedSize+=file.length();
        }
    }

    private void indexDir(File file) throws FileNotFoundException, AccessDeniedException {
        File[] subFiles = file.listFiles();
        if (subFiles != null  && subFiles.length > 0) {
            for(File f:subFiles){
                indexShare(f);
            }
        }
    }


    private void hashingFiles(ArrayList<File> fileList) {
        for(File file:fileList) {
            String fileBodyHashCode = null;
            try {
                fileBodyHashCode = Files.asByteSource(file).hash(fileBodyHashFunction).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileNameHashCode = fileNameHashFunction.newHasher().putString(file.getName(), Charset.forName("UTF8")).hash().toString();
            String fileName = file.getName();
            String filePath = file.getPath();
            Date lastModified = new Date(file.lastModified());
            listOfSharedFiles.add(new FileNode(fileName, fileNameHashCode, filePath, fileBodyHashCode, lastModified));
            finishHashedSize += file.length();
        }
    }

    public long getFinishHashedSize() {
        return finishHashedSize;
    }

    public long getTotalHashedSize() {
        return totalHashedSize;
    }

    public long getStartHashingTime() {
        return startHashingTime;
    }

    public static void testrun(Settings settings) throws FileNotFoundException, AccessDeniedException, ExecutionException, InterruptedException {

        //For test reason
        long startHashingTime = System.currentTimeMillis();
        Path path = Paths.get("D:\\nokia numbers");
        File file = path.toFile();
        FileShared fileShared = new FileShared(file,settings);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future future = executor.submit(fileShared);
        while (!future.isDone()){
            Thread.sleep(1000);
            System.out.println("Hashing files: "
                    + fileShared.getFinishHashedSize()
                    + " / " + fileShared.getTotalHashedSize()
                    + "( " + (fileShared.getFinishHashedSize()*100 / fileShared.getTotalHashedSize())
                    + "% / " + "100%");
        }
        ArrayList<FileNode> fileNodes = fileShared.getListOfSharedFiles();
        fileNodes.forEach(System.out::println);
        for(FileNode fn:fileNodes) {
            DBWriteHandler dbWriteHandler = DBWriteHandler.getInstance();
            dbWriteHandler.addToDB(settings.getSprAppCtx().getBean(FileNodeRepository.class), fn);
        }
        long endHashingTime = System.currentTimeMillis();
        System.out.println("Start hashing time: "+new Date(startHashingTime)
                +"End hashing time: "+new Date(endHashingTime)
                +"Estimated time: "+ (endHashingTime-startHashingTime)+" millis"
                +"Hashing total files: "+fileNodes.size());


    }

}


