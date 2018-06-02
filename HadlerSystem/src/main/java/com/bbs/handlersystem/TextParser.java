package com.bbs.handlersystem;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TextParser {

    private static final char DOT = '.';
    private static final char PAR = '\n';
    private static final char SPACE = ' ';

    @Getter
    @Setter
    @NonNull
    private String fileName;

    @Getter
    @NonNull
    private String text;

    @Getter
    @Setter
    private int sentenceLength;

    @Getter
    @Setter
    private int paragraphLength;

    @Getter
    @Setter
    @NonNull
    private Set<String> exceptionWords;

    @Getter
    @Setter
    @NonNull
    private Set<String> normalWords;

    @Getter
    @Setter
    @NonNull
    private Set<String> tooLongSentences;

    @Getter
    @Setter
    @NonNull
    private Set<String> tooLongParagraphs;

    private void getSettings() {
        this.sentenceLength = 20;
        this.paragraphLength = 60;
        this.fileName = "src/main/input.txt";
        this.normalWords = new HashSet<>();
        this.exceptionWords = new HashSet<>();
        this.tooLongSentences = new HashSet<>();
        this.tooLongParagraphs = new HashSet<>();
        exceptionWords.addAll(Arrays.asList("мы", "я", "очевидно"));
    }

    private void readText() throws FileNotFoundException {
        InputStreamReader is = new FileReader(new File(fileName));
        text = new BufferedReader(is).lines().collect(Collectors.joining("\n"));
    }

    private void parseText() {

        int wordCounter = 0;
        int sentenceCounter = 0;
        int paragraphCounter = 0;

        StringBuilder word = new StringBuilder();
        StringBuilder sentence = new StringBuilder();
        StringBuilder paragraph = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            word.append(currentChar);
            sentence.append(currentChar);
            paragraph.append(currentChar);

            switch (currentChar) {
                case SPACE:
                    checkWord(wordCounter, word);
                    break;
                case DOT:
                    sentenceCounter++;
                    checkWord(wordCounter, word);
                    if (sentenceLength < sentence.length()) {
                        tooLongSentences.add(sentence.toString());
                    }
                    sentence.delete(0, sentence.length());
                    break;
                case PAR:
                    paragraphCounter++;
                    checkWord(wordCounter, word);
                    if (paragraphLength < paragraph.length()) {
                        tooLongParagraphs.add(paragraph.toString());
                    }
                    paragraph.delete(0, paragraph.length());
                    break;
            }
        }
    }

    private void checkWord(int wordCounter, StringBuilder word) {
        wordCounter++;
        word.deleteCharAt(word.length() - 1);
        if (!exceptionWords.contains(word.toString())) {
            normalWords.add(word.toString());
        } else {
            throw new IllegalArgumentException("text contains exception word!");
        }
        word.delete(0, word.length());
    }


    public static void main(String[] args) throws FileNotFoundException {
        TextParser parser = new TextParser();
        parser.getSettings();
        parser.readText();
        parser.parseText();

        System.out.println("Normal words: " + parser.getNormalWords().toString());
        //System.out.println("too long sentences: " + parser.getTooLongSentences().toString());
        //System.out.println("too long paragraphs: " + parser.getTooLongParagraphs().toString());

    }

}

