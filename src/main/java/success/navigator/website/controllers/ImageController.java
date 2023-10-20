package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import success.navigator.website.services.ImageService;

@Controller
@Slf4j
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    // everything secured (/images/add)
    // everything secured (/images/delete)
    @PostMapping("/add")
    private String addStaticImagePost(@RequestAttribute(value = "image_file", required = false) MultipartFile image_file) {
        imageService.addStaticImageToDir(image_file);
        return "redirect:/admin/images";
    }

    @PostMapping("/delete")
    private String deleteStaticImagePost(@RequestParam("image") String image) {
        imageService.deleteStaticImageFromDir(image);
        return "redirect:/admin/images";
    }

}
