(ns seminovos-cd.core
  (:require
      [seminovos-cd.pipeline :as pipeline]
      ;[ring.server.standalone :as ring-server]
      [org.httpkit.server :as http-kit]
      ;[lambdacd.ui.ui-server :as ui]
      [lambdaui.core :as ui]
      [lambdacd.runners :as runners]
      [lambdacd.util :as util]
      [lambdacd.core :as lambdacd]
      [clojure.tools.logging :as log])
  (:gen-class))

(defn -main [& args]
      (let [;; the home dir is where LambdaCD saves all data.
            ;; point this to a particular directory to keep builds around after restarting
            home-dir (util/create-temp-dir)
            config {:home-dir home-dir
                    :name "seminovos cd"}
            ;; initialize and wire everything together
            pipeline (lambdacd/assemble-pipeline pipeline/pipeline-def config)
            ;; create a Ring handler for the UI
            app (ui/ui-for pipeline)]
           (log/info "LambdaCD Home Directory is " home-dir)
           ;; this starts the pipeline and runs one build after the other.
           ;; there are other runners and you can define your own as well.
           (runners/start-one-run-after-another pipeline)
           ;; start the webserver to serve the UI
           ;(ring-server/serve app {:open-browser? false
                                   ;:port 8080})))
           (http-kit/run-server app { :open-browser? false
                                   :port 8080})))
