(ns robot-simulator
    (:require [clojure.set :as set]))

(defn robot [coordinates, bearing]
      {:bearing bearing :coordinates coordinates})

(def directions {:west :north
                 :north :east
                 :east :south
                 :south :west})

(defn turn-right [bearing]
      (bearing directions))

(defn turn-left [bearing]
      (bearing (set/map-invert directions)))

(defn robot-left [rbt] (assoc rbt :bearing (turn-left (:bearing rbt))))

(defn robot-right [rbt] (assoc rbt :bearing (turn-right (:bearing rbt))))

(defn robot-advance [rbt]
      (assoc rbt :coordinates (let [{:keys [coordinates bearing]} rbt]
                                   (case bearing
                                         :east (update coordinates :x inc)
                                         :west (update coordinates :x dec)
                                         :north (update coordinates :y inc)
                                         :south (update coordinates :y dec)
                                         )
                                   ))

      )

(defn letter->action [letter]
      (case letter
            "L" robot-left
            "A" robot-advance
            "R" robot-right))

(defn inst->actions [inst]
      (map letter->action (map str (seq inst))))

(defn simulate [inst rbt]
      ((apply comp (inst->actions (reverse inst))) rbt))