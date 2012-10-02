package hr.element.ocr

import java.io.FileInputStream
import scala.io.Source

object EntryPoint extends App {

  val fileName = "Izvodi_PDF_31-07-2012.pdf"
  val path = "/home/davor/work/ocr/"

  val fis = new FileInputStream(path + fileName)

  val content = Stream.continually(fis.read).takeWhile(-1 !=).map(_.toByte).toArray

//  println(OcrConfig.BaseFolder)

  println(OcrWrapper.toText(fileName, content))

}