package com.company.jmixtest.bank.controllers;

import com.company.jmixtest.bank.service.CardService;
import com.company.jmixtest.bank.service.CardService.CardInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<CardInfo> list() {
        return cardService.listCards();
    }

    @GetMapping("/{number}")
    public CardInfo find(@PathVariable String number) {
        return cardService.findCard(number);
    }

    @PostMapping("/number")
    public CardInfo findByNumber(@RequestBody String number) {
        String value = number.trim();
        if (value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2) {
            value = value.substring(1, value.length() - 1);
        }
        return cardService.findCard(value);
    }

    @PostMapping("/sendBalance")
    public CardInfo sendBalance(@RequestParam String number1, @RequestParam String number2,
                                @RequestParam long value) {
        return cardService.sendBalance(number1, number2, value);
    }

    @PostMapping("/block")
    public CardInfo block(@RequestParam String number) {
        return cardService.blockCard(number);
    }

    @PostMapping("/unblock")
    public CardInfo unblock(@RequestParam String number) {
        return cardService.unblockCard(number);
    }

    @ExceptionHandler(CardService.CardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(CardService.CardNotFoundException error) {
        return Map.of("message", error.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> invalid(IllegalArgumentException error) {
        return Map.of("message", error.getMessage());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> invalidRequest(Exception error) {
        return Map.of("message", "Проверьте номер карты и целую сумму перевода");
    }
}
