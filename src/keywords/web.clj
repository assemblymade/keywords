(ns keywords.web
  (:gen-class)
  (:require [clojure.data.json :as json]
            [compojure.core :refer [defroutes GET POST]]
            [environ.core :refer [env]]
            [keywords.core :as core]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-params]]))

(defonce approved-methods {"rake" core/rake})

(defroutes app-routes
  (GET "/" [] {:status 200
               :headers {"Content-Type" "text/plain"}
               :body "lookin' good"})
  (POST "/:method" {params :params}
        (when-let [method (get approved-methods (params :method))]
          {:status 200
           :headers {"Content-Type" "application/json"}
           :body (json/write-str (method (params :text)))})))

(def app (wrap-json-params
          (wrap-defaults
           app-routes
           (assoc api-defaults
             :keywordize true))))

(defn -main []
  (run-jetty app {:port (Integer. (or (env :port) "10000"))}))
