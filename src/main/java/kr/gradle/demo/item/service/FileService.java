package kr.gradle.demo.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
//로그찍는 최근 데이터
@Slf4j
public class FileService {

//    업로드 경로 실제이름 파일 데이터
    public String uploadFile(String uploadPath,String oriFileName,byte[] fileData) throws IOException {

//      uuid를 자동으로 랜덤으로 만들어준다.
        UUID uuid = UUID.randomUUID();
//      jpg,png,jepg 등등 마지막에서부터 .위치 뒤에 문자열을 다 가져온다.
        String extension = oriFileName.substring(oriFileName.lastIndexOf("."));
//      savedFileName은 랜덤으로 만든 uuid에 원래있는 확장자를 넣어서 만든다.
        String savedFileName = uuid.toString() + extension;
//      업로드 패스에 + 파일 이름으로 저장 uploadpath/xxxxx.jpg
        String fileUploadUrl = uploadPath +"/"+savedFileName;
//       업로드 파일 에 파일을 내보내는 로직
//      파일을 FileOutStream 객체에 넣는다/
        FileOutputStream fileOutputStream = new FileOutputStream(fileUploadUrl);
//      파일의 데이터 (실제 파일 데이터를 바이트배열로 받아와서 넣는다)
        fileOutputStream.write(fileData);
        fileOutputStream.close();

//      저장된 파일 이름 리턴
        return savedFileName;
    }

    public void deleteFile(String filePath){
//      파일을 지우기위한 파일객체
        File deleteFile = new File(filePath);

//       파일이 존재한다면
        if(deleteFile.exists()){
//            제거로직
            deleteFile.delete();
            log.info("파일을 삭제했습니다.");
        }else {
            log.info("파일을 존재하지않습니다.");
        }


    }
}
