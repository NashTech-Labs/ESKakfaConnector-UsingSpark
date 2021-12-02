package com.example

import play.api.libs.json._

case class Customer( id :String, name:String, address : String )


object Customer {

  implicit val _format: OFormat[Customer] = Json.format[Customer]

  def fromJson(jsonStr: String): Option[Customer] = JsonUtils.parseAsOpt[Customer](jsonStr)


}
