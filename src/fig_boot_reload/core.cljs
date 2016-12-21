(ns ^:figwheel-load fig-boot-reload.core
  (:require [figwheel.client.file-reloading :as fig-reload]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  (swap! app-state update-in [:__figwheel_counter] inc)
  (.info js/console "Reloading Javascript...")
  (.warn js/console "Figwheel's meta pragma: " @fig-reload/figwheel-meta-pragmas)
  )
