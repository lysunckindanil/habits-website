package success.navigator.website.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ImageService {
    public final String STATIC_DIR = File.separator + "opt" + File.separator + "navigator" + File.separator + "images";

    public List<String> getStaticImages() {
        File[] file = new File(STATIC_DIR).listFiles();
        return file == null ? new ArrayList<>() : Arrays.stream(file).map(File::getName).toList();
    }

    public void addStaticImageToDir(MultipartFile multipartFile) {
        try {
            multipartFile.transferTo(Path.of(STATIC_DIR + File.separator + multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            log.error("Unable to add image file to static files dir");
            log.error(e.toString());
        }
    }

    public void deleteStaticImageFromDir(@PathVariable String image) {
        try {
            Files.delete(Path.of(STATIC_DIR + File.separator + image));
        } catch (IOException e) {
            log.error("Unable to delete image in static files dir");
            log.error(e.toString());
        }
    }
}
