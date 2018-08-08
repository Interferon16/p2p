package com.simple_p2p.p2p_engine.enginerepository;

import com.simple_p2p.entity.CommonEntity;
import io.netty.util.internal.ConcurrentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public class DBWriteHandler<R extends JpaRepository, E extends CommonEntity> {

    private ConcurrentSet<WriterClass> bufferSetWriteToDB = new ConcurrentSet<>();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private DBWriteHandler() {
    }


    public static class SettingsHolder {
        public static final DBWriteHandler HOLDER_INSTANCE = new DBWriteHandler();
    }

    public static DBWriteHandler getInstance() {
        return DBWriteHandler.SettingsHolder.HOLDER_INSTANCE;
    }

    public synchronized void addToDB(R repository, E entity) {
        /*bufferSetWriteToDB.add(new WriterClass(repository, entity));*/
        if (repository.existsById(entity.getUnique())) {
            repository.deleteById(entity.getUnique());
        }
        repository.saveAndFlush(entity);

    }

/*    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!bufferSetWriteToDB.isEmpty()) {
                for (WriterClass wc : bufferSetWriteToDB) {
                    E entity = wc.getEntity();
                    R repository = wc.getRepository();
                    if (repository.existsById(entity.getUnique())) {
                        repository.deleteById(entity.getUnique());
                    }
                    repository.saveAndFlush(entity);
                    bufferSetWriteToDB.remove(wc);
                }
            }
        }
    }*/

    private class WriterClass {
        private R repository;
        private E entity;

        public WriterClass(R repository, E entity) {
            this.repository = repository;
            this.entity = entity;
        }

        public R getRepository() {
            return repository;
        }

        public E getEntity() {
            return entity;
        }
    }


}
