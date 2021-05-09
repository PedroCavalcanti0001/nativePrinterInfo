import java.util.Arrays;
import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Winspool;
import com.sun.jna.platform.win32.WinDef.INT_PTR;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

public interface WinspoolExt extends Winspool {


    WinspoolExt INSTANCE = (WinspoolExt) Native.loadLibrary("Winspool.drv", WinspoolExt.class,
            W32APIOptions.UNICODE_OPTIONS);

    boolean GetPrinter(HANDLE hPrinter, int Level, Pointer pPrinter, int cbBuf, IntByReference pcbNeeded);

    boolean OpenPrinter(String pPrinterName, HANDLEByReference phPrinter, Pointer pDefault);

    public static class PRINTER_INFO_2 extends Structure {
        public String pServerName;
        public String pPrinterName;
        public String pShareName;
        public String pPortName;
        public String pDriverName;
        public String pComment;
        public String pLocation;
        public INT_PTR pDevMode;
        public String pSepFile;
        public String pPrintProcessor;
        public String pDatatype;
        public String pParameters;
        public INT_PTR pSecurityDescriptor;
        public int Attributes;
        public int Priority;
        public int DefaultPriority;
        public int StartTime;
        public int UntilTime;
        public int Status;
        public int cJobs;
        public int AveragePPM;

        protected List<String> getFieldOrder() {
            return Arrays.asList(new String[] { "pServerName", "pPrinterName", "pShareName", "pPortName",
                    "pDriverName", "pComment", "pLocation", "pDevMode", "pSepFile", "pPrintProcessor", "pDatatype",
                    "pParameters", "pSecurityDescriptor", "Attributes", "Priority", "DefaultPriority", "StartTime",
                    "UntilTime", "Status", "cJobs", "AveragePPM" });
        }

        public PRINTER_INFO_2() {
        }

        public PRINTER_INFO_2(int size) {
            super(new Memory(size));
        }
    }

}