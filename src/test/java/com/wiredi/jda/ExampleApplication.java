package com.wiredi.jda;

import com.wiredi.annotations.ActiveProfiles;
import com.wiredi.runtime.WireRepository;

@ActiveProfiles("local")
public class ExampleApplication {

    public static void main(String[] args) {
        WireRepository.open();
    }

}
