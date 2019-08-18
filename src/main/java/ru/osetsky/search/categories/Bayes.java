package ru.osetsky.search.categories;

import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import org.tartarus.snowball.ext.russianStemmer;
import ru.osetsky.search.utilities.ReaderFile;

import java.util.Arrays;

public class Bayes {

    public static void main(String[] args) {
        // Create a new bayes classifier with string categories and string features.
        Classifier<String, String> bayes = new BayesClassifier<String, String>();

        // Two examples to learn from.
        String[] music = ReaderFile.readArraysOfStringFromFile("music.txt");
        String[] medic = ReaderFile.readArraysOfStringFromFile("medic.txt");

        // Learn by classifying examples.
        // New categories can be added on the fly, when they are first used.
        // A classification consists of a category and a list of features
        // that resulted in the classification in that category.
        bayes.learn("music", Arrays.asList(music));
        bayes.learn("medic", Arrays.asList(medic));

        // Here are two unknown sentences to classify.
        String[] unknownText1 = "медицинский осмотр".split("\\s");
        String[] unknownText2 = "музыкальный композиция".split("\\s");

        // Пример обработки для русского языка стемминга Портрета
        russianStemmer stemmer = new russianStemmer();
        for (String unknownWord:unknownText2) {
            stemmer.setCurrent(unknownWord);
            if (stemmer.stem()){
                System.out.println(stemmer.getCurrent());
            }
        }

        System.out.println( // will output "music"
                bayes.classify(Arrays.asList(unknownText1)).getCategory());
        System.out.println( // will output "medic"
                bayes.classify(Arrays.asList(unknownText2)).getCategory());

        // Get more detailed classification result.
        ((BayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText1));

        // Change the memory capacity. New learned classifications (using
        // the learn method) are stored in a queue with the size given
        // here and used to classify unknown sentences.
        bayes.setMemoryCapacity(500);
    }

    public void test() {
        // Create a new bayes classifier with string categories and string features.
        Classifier<String, String> bayes = new BayesClassifier<String, String>();

        // Two examples to learn from.
        String[] positiveText = "I love sunny days".split("\\s");
        String[] negativeText = "I hate rain".split("\\s");

        // Learn by classifying examples.
        // New categories can be added on the fly, when they are first used.
        // A classification consists of a category and a list of features
        // that resulted in the classification in that category.
        bayes.learn("positive", Arrays.asList(positiveText));
        bayes.learn("negative", Arrays.asList(negativeText));

        // Here are two unknown sentences to classify.
        String[] unknownText1 = "today is a sunny day".split("\\s");
        String[] unknownText2 = "there will be rain".split("\\s");

        System.out.println( // will output "positive"
                bayes.classify(Arrays.asList(unknownText1)).getCategory());
        System.out.println( // will output "negative"
                bayes.classify(Arrays.asList(unknownText2)).getCategory());

        // Get more detailed classification result.
        ((BayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText1));

        // Change the memory capacity. New learned classifications (using
        // the learn method) are stored in a queue with the size given
        // here and used to classify unknown sentences.
        bayes.setMemoryCapacity(500);
    }
}
