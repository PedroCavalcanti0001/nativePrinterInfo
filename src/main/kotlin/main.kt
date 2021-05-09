fun main() {
    val printer = WinspoolUtilExt.getPrinterInfo2().find { it.pShareName == "zebra_nti" }
    if (printer != null) {
        val name = printer.pPrinterName
        println(printer.Status)
        println(printer.cJobs)
        println("${name}=${printer.pShareName}")
    }
}