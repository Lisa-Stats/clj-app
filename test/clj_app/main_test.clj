(ns clj-app.main-test
  (:require
   [clojure.test :refer [deftest is testing]]))

(deftest main-test
  (testing "I never fail"
    (is (= 2 (+ 1 1)))))
