package hr.element.ocr

import scala.sys.process._
import java.io.File
import java.io.FileOutputStream
import java.io.FileInputStream
import scalax.io.Codec

object OcrWrapper {
  import OcrConfig._

  def toText(fileName: String, content: Array[Byte]) = {

    write(fileName, content) match {
      case Some(basePath) =>{
        val inPath   = basePath + FS + OutPgmName
        val outPath  = basePath + FS + OutPgmName
        val wlPath   = WhiteList

        toPPM(basePath, fileName)

        val exec = "tesseract -l hrv %s %s nobatch %s" format(inPath, outPath, wlPath)

        Seq("bash", "-c", exec).!

        val fis = new FileInputStream(basePath + FS + OutTxtName)
        val txt = new String(Stream.continually(fis.read).takeWhile(-1 !=).map(_.toByte).toArray, "UTF-8")
        fis.close()

        Option(trim(txt))
      }
      case None => None
    }
  }

  private def toPPM(basePath: String, fileName: String) = {
    val inPath  = basePath + FS + fileName
    val outPath = basePath + FS + "out"


    val exec = "pdftoppm -gray -r 400 -x 1952 -W 1334 -y 603 -H 585 %s %s" format(inPath, outPath)
    Seq("bash", "-c", exec).!!
  }

  private def write(name: String, content: Array[Byte]) = {

    val path = BaseFolder + FS + name
    val dir  = new File(path)

    dir.mkdir() match {
      case false => None
      case true  => {
        val fos  = new FileOutputStream(path + FS + name)
        fos.write(content)
        fos.close()
        Option(path)
      }
    }
  }

  private def trim(text: String) = {
    val newLineRegex = """\n"""r
    val nl = "\n"
    val name :: address :: zip :: _  = newLineRegex split text toList

    name + nl + address + nl +zip
  }

}
