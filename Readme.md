# Document Search using OpenNLP Porter Stemmer with HashMap

This repository contains source files that utilize the OpenNLP Porter Stemmer and implement a HashMap data structure for searching documents. This implementation is developed for information retrieval.

## Contents

- [Introduction](#introduction)
- [Dependencies](#dependencies)
- [Usage](#usage)
- [License](#license)

## Introduction

The OpenNLP Porter Stemmer is a powerful tool for stemming words in text documents. Stemming is the process of reducing words to their root or base form, which can be helpful in various NLP applications like text classification, information retrieval, and search engines. In my usecase it's for information retrieval.

Additionally, this project includes an implementation of a HashMap data structure. HashMaps are widely used for storing key-value pairs and are efficient for quick retrieval of data.

## Dependencies

Before using the source files, make sure you have the following dependencies installed:

- OpenNLP library

## Usage

All of these files will use different Utils methods at different times.

The Purpose of having a TXT file was just for saving progress at anytime of processing.

To use these source files in your project, follow these steps:

1. Clone this repository to your local machine:

   ```bash
   git clone
   ```

2. Install the required dependencies as mentioned in the Dependencies section.

3. Include the necessary source files in your project.

4. Create a stoplist.txt in the stoplist folder that remove redundant terms

5. Currently the stoplist.java takes 3 input txt files (input1,input2,input3) and removes terms inside them.

6. Then it creates 3 txt files (output1,output2,output3) read by the stemmer.

7. The Stemmer will use openNLP Porter stemmer to modify terms and creates 3 files (stemmed_output1,stemmed_output2,stemmed_output3).

8. Then in WordMatrix we take the stemmed outputs and create 3 txt files (wordMatrix1,wordMatrix2,wordMatrix3)

9. At the end we use queryTry to query a statement from the wordMatrix files.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
