package com.jezz.docker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {

    @RequestMapping("/docker")
    @ResponseBody
    String docker(String name) {
        return "Hello Docker !!!   Hello K8s !!!" ;
    }
}
