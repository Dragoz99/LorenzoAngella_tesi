import com.google.common.io.Files;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NLP {
    public static void main(String[] args) throws IOException {


        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text from the file..
        File inputFile = new File("src/test/resources/target/sample-content.txt");
        String text = Files.asCharSource(inputFile, Charset.forName("UTF-8")).read();


        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);



        //---  liste contenitore
        List<String> NN_list = new ArrayList<>(); // NN (nouns)
        List<String> NP_list = new ArrayList<>(); // NP (noun parts)
        List<String> VB_list = new ArrayList<>(); // VB (verbs)
        List<String> JJ_list = new ArrayList<>(); // JJ (adjetives)

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);

                if(pos.equalsIgnoreCase("NN")){
                    NN_list.add(word);
                }
                if(pos.equalsIgnoreCase("VB")){
                    VB_list.add(word);
                }
                if(pos.equalsIgnoreCase("JJ")){
                    JJ_list.add(word);
                }

            }

            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println("parse tree:\n" + tree);

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            System.out.println("dependency graph:\n" + dependencies);

            String pippo = "test";
            File file_uml = new File("C:\\Users\\loren\\Desktop\\UNIVERSITà\\Tesi\\LorenzoAngella_tesi\\src\\test\\resources"+pippo+".puml");

            //controllo creazione file
            boolean result;

            try{
                result = file_uml.createNewFile();
                if(result){
                    System.out.println("file created "+ file_uml.getCanonicalPath()); // ritorna il percorso
                }else{
                    System.out.println("File already exist at location"+ file_uml.getCanonicalPath());
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try{

             // Classe pro = new Classe("gigi");

                FileWriter writer = new FileWriter(file_uml);
                writer.write("@startuml");
                writer.write("\n");

              //  writer.write(pro.print_ogg());




                writer.write("\n");
                //writer.write("class " +lista_nomi.get(0).getTarget().originalText() );
                //writer.write("\n");
                writer.write("@enduml");
                writer.close();

            }catch (Exception e){

            }
    }
    }
}
