package org.stellar.reference.event

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.hc.core5.http.HttpStatus
import org.stellar.anchor.api.callback.SendEventResponse
import org.stellar.anchor.util.GsonUtils
import org.stellar.reference.data.SendEventRequest

fun Route.event(eventService: EventService) {
  val gson: Gson = GsonUtils.getInstance()

  route("/event") {
    // The `POST /event` endpoint of the Callback API to receive an event.
    post {
      val receivedEventJson = call.receive<String>()
      val receivedEvent = gson.fromJson(receivedEventJson, SendEventRequest::class.java)
      eventService.processEvent(receivedEvent)
      call.respond(gson.toJson(SendEventResponse(HttpStatus.SC_OK, "event processed")))
    }
  }
  route("/events") {
    // Test endpoint to get the events recorded by the reference server.
    // The `txnId` parameter is optional. If it is provided, only the events with the given `txnId`
    // will be returned.
    get { call.respond(gson.toJson(eventService.getEvents(call.parameters["txnId"]))) }
    // Test endpoint to clear the events recorded by the reference server.
    delete {
      eventService.clearEvents()
      call.respond("Events cleared")
    }
  }
  route("/events/latest") {
    // Test endpoint to get the latest event recorded by the reference server.
    get {
      val latestEvent = eventService.getLatestEvent()
      if (latestEvent != null) {
        call.respond(gson.toJson(latestEvent))
      } else {
        call.respond(HttpStatusCode.NotFound)
      }
    }
  }
}
