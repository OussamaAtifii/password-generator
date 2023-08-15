package com.example.generator.services;

import com.example.generator.models.Password;
import com.example.generator.repositories.PasswordRepository;

import java.security.SecureRandom;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public PasswordRepository passwordRepository;

    public PasswordService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    /**
     * Obtiene una lista de todas las contraseñas almacenadas en el repositorio.
     *
     * @return Una lista de objetos Password que representa todas las contraseñas
     *         almacenadas.
     */
    public List<Password> getPasswords() {
        return passwordRepository.findAll();
    }

    /**
     * Genera una contraseña y la guarda en la base de datos si no existe.
     *
     * @param length Longitud deseada para la contraseña.
     * @param lower  Indica si se permiten letras minúsculas en la contraseña.
     * @param upper  Indica si se permiten letras mayúsculas en la contraseña.
     * @param nums   Indica si se permiten números en la contraseña.
     * @param chars  Indica si se permiten caracteres especiales en la contraseña.
     * @return ResponseEntity con un HashMap que contiene la contraseña generada y
     *         un mensaje de éxito, o un mensaje de error si la contraseña no puede
     *         ser generada.
     */
    public ResponseEntity<HashMap<String, Object>> getPassword(int length, boolean lower, boolean upper, boolean nums,
            boolean chars) {
        HashMap<String, Object> data = new HashMap<>();
        String generatedPass = generatePassword(length, lower, upper, nums, chars);

        if (!generatedPass.isBlank()) {
            Password password = new Password(generatedPass);
            Optional<Password> res = passwordRepository.findByPassword(password.getPassword());
            if (res.isEmpty()) {
                passwordRepository.save(password);
            }

            data.put("password", password.getPassword());
            data.put("message", "Password successfully generated.");

        } else {
            data.put("error", true);
            data.put("message", "The password cannot be generated with the data entered");
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * Genera una lista de contraseñas aleatorias con la longitud y tipos de
     * caracteres especificados.
     *
     * @param length La longitud deseada para las contraseñas generadas.
     * @param lower  Indica si se permiten letras minúsculas en las contraseñas.
     * @param upper  Indica si se permiten letras mayúsculas en las contraseñas.
     * @param nums   Indica si se permiten números en las contraseñas.
     * @param chars  Indica si se permiten caracteres especiales en las contraseñas.
     * @return ResponseEntity con un HashMap que contiene una lista de contraseñas
     *         generadas y un mensaje de éxito, o un mensaje de error si las
     *         contraseñas no pueden ser generadas.
     */
    public ResponseEntity<HashMap<String, Object>> getPasswordList(int length, boolean lower, boolean upper,
            boolean nums,
            boolean chars) {
        HashMap<String, Object> data = new HashMap<>();
        List<Password> passwordList = new ArrayList<>();
        String generatedPass = generatePassword(length, lower, upper, nums, chars);

        if (!generatedPass.isBlank()) {
            for (int i = 0; i < 10; i++) {
                Password password = new Password(generatePassword(length, lower, upper, nums, chars));
                passwordRepository.save(password);
                passwordList.add(password);
            }
            data.put("passwordList", passwordList);
            data.put("message", "Password successfully generated");
        } else {
            data.put("error", true);
            data.put("message", "The password cannot be generated with the data entered");

        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * Genera una contraseña aleatoria con la longitud especificada y los tipos de
     * caracteres seleccionados.
     *
     * @param length La longitud deseada para la contraseña.
     * @param lower  Indica si se permiten letras minúsculas en la contraseña.
     * @param upper  Indica si se permiten letras mayúsculas en la contraseña.
     * @param nums   Indica si se permiten números en la contraseña.
     * @param chars  Indica si se permiten caracteres especiales en la contraseña.
     * @return Una cadena que representa la contraseña generada.
     */
    public static String generatePassword(int length, boolean lower, boolean upper, boolean nums, boolean chars) {
        final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        final String NUMBERS = "0123456789";
        final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|\\\\;:'\\\",./<>?`~¡¿";

        StringBuilder password = new StringBuilder();
        StringBuilder strPossibilities = new StringBuilder();
        Random random = new SecureRandom();

        if (lower) {
            strPossibilities.append(LOWERCASE);
        }
        if (upper) {
            strPossibilities.append(UPPERCASE);
        }
        if (nums) {
            strPossibilities.append(NUMBERS);
        }
        if (chars) {
            strPossibilities.append(SPECIAL_CHARS);
        }

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(strPossibilities.length());
            password.append(strPossibilities.charAt(index));
        }

        return password.toString();
    }

     /**
     * Calcula la puntuación de seguridad de una contraseña basada en diversos
     * criterios.
     *
     * @param contrasena La contraseña que se va a evaluar.
     * @return La puntuación de seguridad calculada para la contraseña.
     */
    public int getSec(String contrasena) {

        int cantidad = 0;
        int contadorEspeciales = 0;

        int contadorDigitos = contrasena.replaceAll("\\D", "").length();

        boolean tieneMayus = false;
        boolean tieneMinus = false;
        boolean tieneDigitos = false;

        if (contadorDigitos >= 2) {
            cantidad++;
            System.out.println("2 dig");
        }

        for (char c : contrasena.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayus = true;
            } else if (Character.isLowerCase(c)) {
                tieneMinus = true;
            } else if (Character.isDigit(c)) {
                tieneDigitos = true;
            } else {
                contadorEspeciales++;
                System.out.println("especiales ++");
            }
        }

        // Evaluar la longitud de la contraseña y asignar puntos
        if (contrasena.length() >= 8 && contrasena.length() <= 11) {
            cantidad += 1;
            System.out.println("8 long");
        } else if (contrasena.length() >= 12 && contrasena.length() <= 19) {
            cantidad += 2;
            System.out.println("12 long");

        } else if (contrasena.length() >= 20) {
            cantidad += 3;
            System.out.println("20 long");

        }

        if (contadorEspeciales == 1) {
            cantidad += 1;
            System.out.println("1 especial");
        } else if (contadorEspeciales >= 3) {
            cantidad += 2;
            System.out.println("3 especial");
        }

        // Contiene minusculas o mayusculas y digitos
        // Contiene digitos, minusculas y mayusculas
        if (tieneDigitos && tieneMinus && tieneMayus) {
            cantidad += 2;
            System.out.println("todo");
        } else if ((tieneMinus || tieneMayus) && tieneDigitos) {
            cantidad += 1;
            System.out.println("min o may");
        }

        return cantidad;
    }

    public Map<String, Object> determinarSeguridad(int cantidad) {
        Map<String, Object> data = new HashMap<>();

        data.put("puntuacion", cantidad);

        if (cantidad >= 1 && cantidad <= 2) {
            data.put("seguridad", "Muy débil");
        }
        if (cantidad >= 3 && cantidad <= 4) {
            data.put("seguridad", "Débil");
        }
        if (cantidad >= 5 && cantidad <= 6) {
            data.put("seguridad", "Media");
        }
        if (cantidad >= 7 && cantidad <= 8) {
            data.put("seguridad", "Alta");
        }

        return data;
    }

}
