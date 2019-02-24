(set-env!
 :source-paths #{"src"}
 :resource-paths #{"assets"}
 :dependencies '[[org.clojure/clojure         "1.8.0"]
                 [adzerk/boot-cljs "2.1.5" :scope "test"]
                 [powerlaces/boot-figreload "0.5.14-SNAPSHOT" :scope "test"]

                 [pandeiro/boot-http "0.7.6" :scope "test"]
                 [binaryage/dirac "RELEASE" :scope "test"]
                 [binaryage/devtools "RELEASE" :scope "test"]
                 [powerlaces/boot-cljs-devtools "0.2.0" :scope "test"]

                 [adzerk/boot-cljs-repl "0.4.0" :scope "test"]
                 [cider/piggieback "0.4.0"  :scope "test"]
                 [weasel "0.7.0"  :scope "test"]
                 [nrepl "0.6.0" :scope "test"]

                 ;; App deps
                 [org.clojure/clojurescript "1.9.908"  :scope "test"]
                 [prismatic/dommy "1.1.0" :scope "test"]])

(task-options! pom {:project "figreload-demo"
                    :version "0.1.0-SNAPSHOT"
                    :url "https://github.com/arichiardi/figreload-demo"
                    :description "A sample project for trying lein-figwheel integration in boot-reload."
                    :license {:name "Unlicense"
                              :url "http://unlicense.org/"
                              :distribution :repo}})

(require '[adzerk.boot-cljs              :refer [cljs]]
         '[adzerk.boot-cljs-repl         :refer [cljs-repl cljs-repl-env]]
         '[powerlaces.boot-figreload     :refer [reload]]
         '[powerlaces.boot-cljs-devtools :refer [dirac cljs-devtools]]
         '[pandeiro.boot-http            :refer [serve]])

(deftask testing []
  (merge-env! :resource-paths #{"test"})
  identity)

(deftask dev
  [D with-dirac      bool "Enable Dirac Devtools."
   p port       PORT int  "The nRepl port"]
  (comp (serve :dir "assets/")
        (watch)
        (notify)
        (cljs-devtools)
        (reload :client-opts {:debug true}
                :asset-path "/public") ;; Deprecated
        (if-not with-dirac
          (cljs-repl :nrepl-opts {:port port})
          (dirac :nrepl-opts {:port port}))
        (cljs :optimizations :none
              :source-map true
              :compiler-options {:external-config
                                 {:devtools/config {:features-to-install [:formatters :hints]
                                                    :fn-symbol "λ"
                                                    :print-config-overrides true}}})))

(deftask build-dev []
  (cljs :optimizations :none
        :source-map true))
