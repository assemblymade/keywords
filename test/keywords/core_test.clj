(ns keywords.core-test
  (:require [clojure.test :refer :all]
            [keywords.core :refer :all]))

(deftest test-rake
  (is (not= (rake "Just making sure everything runs.") nil)))
