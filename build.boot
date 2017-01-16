(set-env!
 :source-paths #{"src"}
 :resource-paths #{"assets"}
 :dependencies '[[org.clojure/clojure         "1.8.0"]
                 [adzerk/boot-cljs "2.0.0-SNAPSHOT" :scope "test"]
                 [powerlaces/boot-figreload "0.1.0-SNAPSHOT" :scope "test"]

                 [pandeiro/boot-http "0.7.6" :scope "test"]
                 [crisptrutski/boot-cljs-test "0.2.2" :scope "test"]

                 [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                 [com.cemerick/piggieback "0.2.1"  :scope "test"]
                 [weasel "0.7.0"  :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]

                 ;; App deps
                 [org.clojure/clojurescript "1.9.293"  :scope "test"]
                 [prismatic/dommy "1.1.0" :scope "test"]])

(task-options! pom {:project "figreload-demo"
                    :version "0.1.0-SNAPSHOT"
                    :url "https://github.com/arichiardi/figreload-demo"
                    :description "A sample project for trying lein-figwheel integration in boot-reload."
                    :license {:name "Unlicense"
                              :url "http://unlicense.org/"
                              :distribution :repo}})

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[powerlaces.boot-figreload    :refer [reload]]
 '[crisptrutski.boot-cljs-test  :refer [exit! test-cljs]]
 '[pandeiro.boot-http    :refer [serve]])

(deftask testing []
  (merge-env! :resource-paths #{"test"})
  identity)

(deftask auto-test []
  (comp (testing)
        (watch)
        (speak)
        (test-cljs)))

(deftask dev []
  (comp (serve :dir "assets/")
        (watch)
        (notify)
        (reload :client-opts {:debug true})
        (cljs-repl :nrepl-opts {:port 5055})
        (cljs :source-map true :optimizations :none)))

(deftask test []
  (comp (testing)
        (test-cljs)
        (exit!)))

(deftask build []
  (cljs :optimizations :advanced))
