/*
 * Copyright 2014 Mohd Azeem.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Client_Models;

import static Client_Controllers.ClientController.docPaths_to_ids_map;
import static Client_Controllers.ClientController.documents_len_table;
import static Client_Controllers.ClientController.installation_directory_path;
import static Client_Controllers.ClientController.inverted_index;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

/**
 *
 * @author Mohd Azeem
 */
public class StatisticsSerializer {

    public static void saveStatisticObjects() {

        try {
            System.out.println("saving serialized objects..");
            // save inverted_index
            FileOutputStream fileOut1 = new FileOutputStream(installation_directory_path + "\\index.kh");
            ObjectOutputStream index_out = new ObjectOutputStream(fileOut1);
            index_out.writeObject(inverted_index);
            index_out.close();
            fileOut1.close();
            // save doc_length_table as the serialized object
            FileOutputStream fileOut2 = new FileOutputStream(installation_directory_path + "\\docLenTable.kh");
            ObjectOutputStream doc_len_table_out = new ObjectOutputStream(fileOut2);
            doc_len_table_out.writeObject(documents_len_table);
            doc_len_table_out.close();
            fileOut2.close();
            // now save docPaths_to_ids_map
            FileOutputStream fileOut3 = new FileOutputStream(installation_directory_path + "\\DocIdKeyMap.kh");
            ObjectOutputStream docPaths_to_ids_map_out = new ObjectOutputStream(fileOut3);
            docPaths_to_ids_map_out.writeObject(docPaths_to_ids_map);
            docPaths_to_ids_map_out.close();
            fileOut3.close();
//            logger.log("serialized objects are saved after indexing\n");
        } catch (IOException e) {
            //logger.log("Error.. \t serialized objects are not saved after indexing\n");
        }
    }
    
    public static boolean loadStatisticsObjects(){
        try {
            System.out.println("loading serialized objects..");
            FileInputStream fileInDocLenTable = new FileInputStream(installation_directory_path + "\\docLenTable.kh");
            FileInputStream fileInIndex = new FileInputStream(installation_directory_path + "\\index.kh");
            FileInputStream fileIn_docPaths_to_ids_map = new FileInputStream(installation_directory_path + "\\DocIdKeyMap.kh");
            ObjectInputStream in_index = new ObjectInputStream(fileInIndex);
            ObjectInputStream in_doc_len_table = new ObjectInputStream(fileInDocLenTable);
            ObjectInputStream in_docPaths_to_ids_map = new ObjectInputStream(fileIn_docPaths_to_ids_map);
            inverted_index = (TreeMap<String, TreeMap<Integer,Integer>>) in_index.readObject();
            documents_len_table = (TreeMap<Integer, Integer>) in_doc_len_table.readObject();
            docPaths_to_ids_map = (TreeMap<String, Integer>) in_docPaths_to_ids_map.readObject();
            in_index.close();
            in_doc_len_table.close();
            in_docPaths_to_ids_map.close();
            fileInIndex.close();
            fileInDocLenTable.close();
            fileIn_docPaths_to_ids_map.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
            return false;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("IOException or ClassNotFoundException");
            return false;
        }
    }
}
