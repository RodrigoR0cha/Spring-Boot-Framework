package com.api.parkingcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication // classe de inicialização
@RestController
public class ParkingControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingControlApplication.class, args);

		/* 
			Arquivo principal da aplicação aonde importo todas as bibliotecas necessarias, para
			poder utilizar no projeto
		 */
	}		
}
