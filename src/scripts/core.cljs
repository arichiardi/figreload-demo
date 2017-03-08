(ns ^:figwheel-load scripts.core
  (:require [dommy.core :as dommy]))

(enable-console-print!)

(defonce app-state (atom {:name "User"}))

(swap! app-state assoc :name "Andrea")

(defn on-js-reload []
  (.info js/console "Reloading Javascript...")
  (dommy/set-text! (dommy/sel1 ".greetee-name") (:name @app-state)))
