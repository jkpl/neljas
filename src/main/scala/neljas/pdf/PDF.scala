package neljas.pdf

import io.github.cloudify.scala.spdf._
import java.io._
import java.util.concurrent.Executors
import scalaz.concurrent.Task

object PDF {

  implicit val pdfThreadPool = Executors.newFixedThreadPool(2)

  val pdf = Pdf(new PdfConfig {
    userStyleSheet := "static/pdf.css"
    marginTop := "0mm"
    marginBottom := "0mm"
    marginLeft := "0mm"
    marginRight := "0mm"
  })

  def generate(page: String): Task[Array[Byte]] = Task.fork {
    val output = new ByteArrayOutputStream
    val result = pdf.run(page, output)

    if (result == 0) Task.now(output.toByteArray)
    else Task.fail(new RuntimeException(s"PDF generation failed. Result code: $result"))
  }
}
