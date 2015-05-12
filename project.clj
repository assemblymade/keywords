(defproject keywords "0.1.0-SNAPSHOT"
  :description "Algorithmically extract keywords from text"
  :url "https://github.com/assemblymade/keywords"
  :license {:name "GNU Affero General Public License, Version 3"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.6"]
                 [compojure "1.3.3"]
                 [environ "1.0.0"]
                 [ring/ring-defaults "0.1.4"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [ring/ring-json "0.3.1"]]
  :min-lein-version "2.5.0"
  :plugins [[lein-environ "1.0.0"]
            [lein-ring "0.9.3"]]
  :main ^:skip-aot keywords.web
  :profiles {:uberjar {:aot :all}}
  :uberjar-name "keywords.standalone.jar"
  :repl-options {:timeout 360000}
  :ring {:handler keywords.web/app})
