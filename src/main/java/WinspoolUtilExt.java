import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import com.sun.jna.platform.win32.WinspoolUtil;
import com.sun.jna.ptr.IntByReference;

public class WinspoolUtilExt extends WinspoolUtil {

    public static WinspoolExt.PRINTER_INFO_2[] getPrinterInfo2() {
        IntByReference pcbNeeded = new IntByReference();
        IntByReference pcReturned = new IntByReference();
        WinspoolExt.INSTANCE.EnumPrinters(WinspoolExt.PRINTER_ENUM_LOCAL, null, 2, null, 0, pcbNeeded, pcReturned);
        if (pcbNeeded.getValue() <= 0) {
            return new WinspoolExt.PRINTER_INFO_2[0];
        }

        WinspoolExt.PRINTER_INFO_2 pPrinterEnum = new WinspoolExt.PRINTER_INFO_2(pcbNeeded.getValue());
        if (!WinspoolExt.INSTANCE.EnumPrinters(WinspoolExt.PRINTER_ENUM_LOCAL, null, 2, pPrinterEnum.getPointer(),
                pcbNeeded.getValue(), pcbNeeded, pcReturned)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }

        pPrinterEnum.read();

        return (WinspoolExt.PRINTER_INFO_2[]) pPrinterEnum.toArray(pcReturned.getValue());
    }

    public static WinspoolExt.PRINTER_INFO_2 getPrinterInfo2(String printerName) {
        IntByReference pcbNeeded = new IntByReference();
        IntByReference pcReturned = new IntByReference();
        HANDLEByReference pHandle = new HANDLEByReference();

        WinspoolExt.INSTANCE.OpenPrinter(printerName, pHandle, (Pointer) null);

        WinspoolExt.INSTANCE.GetPrinter(pHandle.getValue(), 2, null, 0, pcbNeeded);
        if (pcbNeeded.getValue() <= 0) {
            return new WinspoolExt.PRINTER_INFO_2();
        }

        WinspoolExt.PRINTER_INFO_2 pinfo2 = new WinspoolExt.PRINTER_INFO_2(pcbNeeded.getValue());

        WinspoolExt.INSTANCE.GetPrinter(pHandle.getValue(), 2, pinfo2.getPointer(), pcbNeeded.getValue(), pcReturned);

        pinfo2.read();
        return pinfo2;
    }

}