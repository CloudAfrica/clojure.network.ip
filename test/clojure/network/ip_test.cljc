(ns clojure.network.ip-test
  (:require [clojure.test :refer [deftest is testing]]
            [clojure.network.ip :as sut]))

(deftest make-ip-address
  (testing "make-ip-address can handle negative IPs"
   (let [ip-str "192.168.0.1"]
    (is (= ip-str
           (-> ip-str
               (sut/make-ip-address)
               (sut/numeric-value)
               (sut/make-ip-address)
               (str)))))))

(deftest dec-ip->byte-array
  (testing "Number is correctly converted to bytes for the lowest IPv4"
    (let [dec-ip 0]
      (is (= [(byte 0) (byte 0) (byte 0) (byte 0)]
             (seq (sut/dec-ip->byte-array dec-ip))))))
  (testing "Number is correctly converted to bytes for the localhost IPv4"
    (let [dec-ip 2130706433]
      (is (= [(byte 127) (byte 0) (byte 0) (byte 1)]
             (seq (sut/dec-ip->byte-array dec-ip))))))
  (testing "Number is correctly converted to bytes for the highest integer IPv4"
    (let [dec-ip 2164260862]
      (is (= [(byte -128) (byte -1) (byte -1) (byte -2)]
             (seq (sut/dec-ip->byte-array dec-ip))))))
  (testing "Number is correctly converted to bytes for the highest integer IPv4 + 1"
    (let [dec-ip 2164260863]
      (is (= [(byte -128) (byte -1) (byte -1) (byte -1)]
             (seq (sut/dec-ip->byte-array dec-ip))))))
  (testing "Number is correctly converted to bytes for the highest IPv4"
    (let [dec-ip 4294967295]
      (is (= [(byte -1) (byte -1) (byte -1) (byte -1)]
             (seq (sut/dec-ip->byte-array dec-ip))))))
  (testing "Number is correctly converted to bytes for the lowest IPv6"
    (let [dec-ip 4294967296]
      (is (= [(byte 0) (byte 0) (byte 0) (byte 0)
              (byte 0) (byte 0) (byte 0) (byte 0)
              (byte 0) (byte 0) (byte 0) (byte 1)
              (byte 0) (byte 0) (byte 0) (byte 0)]
             (seq (sut/dec-ip->byte-array dec-ip))))))
  (testing "Number is correctly converted to bytes for the highest IPv6"
    (let [dec-ip 340282366920938463463374607431768211455]
      (is (= [(byte -1) (byte -1) (byte -1) (byte -1)
              (byte -1) (byte -1) (byte -1) (byte -1)
              (byte -1) (byte -1) (byte -1) (byte -1)
              (byte -1) (byte -1) (byte -1) (byte -1)]
             (seq (sut/dec-ip->byte-array dec-ip)))))))

