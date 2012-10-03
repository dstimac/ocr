package hr.element.ocr

import org.streum.configrity._
import scala.io.Codec

object OcrConfig {

  val FS             = System.getProperty("file.separator")
  val UserHome       = System.getProperty("user.home")
  val OcrConfigPath  = UserHome + FS + ".config" + FS + "element" + FS + "ocr"
  val WhiteList      = OcrConfigPath +  FS + "cro-upper.txt"
  val OcrConfig      = OcrConfigPath + FS + "ocr.config"
  val Config         = Configuration.load(OcrConfig)(Codec.UTF8)
  val BaseFolder     = Config[String]("ocr.baseFolder")
  val OutPgmName     = "out-1.pgm"
  val OutTxtName     = "out-1.pgm.txt"
}