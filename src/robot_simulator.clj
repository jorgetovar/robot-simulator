(ns robot-simulator
    (:require [clojure.set :as set]))

(defn robot [coordinates, bearing]
      {:bearing bearing :coordinates coordinates})

(def directions {:west :north :north :east :east :south :south :west})

(defn turn-right [bearing] (bearing directions))

(defn turn-left [bearing] (bearing (set/map-invert directions)))

(defn advance [bearing coordinates]
      (case bearing
            :east (update coordinates :x inc)
            :west (update coordinates :x dec)
            :north (update coordinates :y inc)
            :south (update coordinates :y dec)
            ))

(defn robot-left [rbt] (assoc rbt :bearing (turn-left (:bearing rbt))))

(defn robot-right [rbt] (assoc rbt :bearing (turn-right (:bearing rbt))))

(defn robot-advance [rbt] (assoc rbt :coordinates (advance (:bearing rbt) (:coordinates rbt))))

(defn letter->action [letter]
      (case letter
            \L robot-left
            \A robot-advance
            \R robot-right))

(defn simulate [instructions rbt] (reduce #((letter->action %2) %1) rbt instructions))