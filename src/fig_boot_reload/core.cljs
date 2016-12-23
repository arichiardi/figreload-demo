(ns ^:figwheel-load fig-boot-reload.core
  (:require [clojure.string :as str]
            [figwheel.client.file-reloading :as fig-reload]
            [dommy.core :as dommy]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce aapp-state (atom {:name "User"}))

;; (swap! app-state assoc :name "Andrea")

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  (.info js/console "Reloading Javascript...")
  (.debug js/console "Figwheel's meta pragma: " @fig-reload/figwheel-meta-pragmas)
  (dommy/set-text! (dommy/sel1 ".greetee-name") (:name @app-state)))
