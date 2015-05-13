(ns keywords.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:use [clojure.java.io]))

(defonce word-regex #"[^a-zA-Z0-9_\+\-/]")
(defonce sentence-regex #"[.!?,;:\t\\\"\(\)\']|\s[\-|—]\s")

(defn build-stopwords []
  (let [stopwords (atom [])]
    (with-open [r (io/reader "resources/stopwords.txt")]
      (doseq [l (line-seq r)]
        (if (not= (.indexOf l "#") 0)
          (swap! stopwords conj l))))
    @stopwords))

(defn tokenize [s]
  (let [lower-s (string/lower-case s)]
    (try
      (string/trim
       (string/split
        lower-s
        word-regex))
      (catch Exception e lower-s))))

(defn- freqs
  "Returns a map of frequencies of
  tokens in document d"
  [d]
  (frequencies (map str (tokenize d))))

(defn f
  "Calculates the raw frequency of
  term t in document d"
  [t d]
  (or (get (freqs d) (string/lower-case t))
      0))

(defn max-f
  "Calculates the maximum raw frequncy
  across all terms in a document d"
  [d]
  (apply max (vals (freqs d))))

(defn tf
  "Calculates the term frequency of
  term t in document d such that
  longer documents receive no bias"
  [t d]
  (+ 0.5 (/ (* 0.5 (f t d))
            (max-f d))))

#_(defn total
  "Counts the number of documents in
  corpus c"
  [c]
  1)

#_(defn containing
  "Counts the number of documents in
  corpus c containing term t"
  [t c]
  1)

#_(defn idf
  "Calculates the inverse document frequency
  of a term t in corpus c"
  [t c]
  (Math/log (/ (total c)
               (containing t c))))

(defn- longer-than
  "Returns true if string s is longer than
  length l"
  [l s]
  (> (count s) l))

(defn stopword-re
  "Builds a regex matching all stopwords sws"
  [sws]
  (re-pattern
   (string/join "|" (map #(str "\\b" % "(?![\\w-])") sws))))

(defn phrases
  "Generates phrases from the string s
  by breaking on stopwords swords"
  [swords s]
  (let [re (stopword-re swords)]
    (filter (partial longer-than 3)
            (map string/trim (string/split s re)))))

(defn sentences
  "Splits string s into sentences"
  [s]
  (map string/trim (string/split s sentence-regex)))

(defn score
  "Returns the term-frequency score in
  document d of phrase p"
  [d p]
  (reduce #(/ (+ %1 (tf %2 d))
              (count p))
          0 p))

(defn rake
  "Returns a sorted list of keywords
  in document d based on the Rapid Automatic 
  Keyword Extraction algorithm"
  [d]
  (let [sents (sentences d)
        sws (build-stopwords)
        kws (filter not-empty (map (partial phrases sws) sents))
        scores (reduce #(assoc %1 %2 (score d %2)) {} kws)]
    (sort-by val > scores)))
