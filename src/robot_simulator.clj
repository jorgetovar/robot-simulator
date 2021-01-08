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

(defn movement [rbt letter]
      (case letter
            \L (assoc rbt :bearing (turn-left (:bearing rbt)))
            \A (assoc rbt :coordinates (advance (:bearing rbt) (:coordinates rbt)))
            \R (assoc rbt :bearing (turn-right (:bearing rbt)))
            ))

(defn simulate [instructions rbt] (reduce #(movement %1 %2) rbt instructions))