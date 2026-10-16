package retrofit.mime;

import com.digits.sdk.vcard.VCardBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class MultipartTypedOutput implements TypedOutput {
    private final String boundary;
    private final byte[] footer;
    private long length;
    private final List<MimePart> mimeParts;

    @Override // retrofit.mime.TypedOutput
    public String fileName() {
        return null;
    }

    private static final class MimePart {
        private final TypedOutput body;
        private final String boundary;
        private boolean isBuilt;
        private final boolean isFirst;
        private final String name;
        private byte[] partBoundary;
        private byte[] partHeader;

        public MimePart(String str, TypedOutput typedOutput, String str2, boolean z) {
            this.name = str;
            this.body = typedOutput;
            this.isFirst = z;
            this.boundary = str2;
        }

        public void writeTo(OutputStream outputStream) throws IOException {
            build();
            outputStream.write(this.partBoundary);
            outputStream.write(this.partHeader);
            this.body.writeTo(outputStream);
        }

        public long size() {
            build();
            if (this.body.length() > -1) {
                return this.body.length() + ((long) this.partBoundary.length) + ((long) this.partHeader.length);
            }
            return -1L;
        }

        private void build() {
            if (this.isBuilt) {
                return;
            }
            this.partBoundary = MultipartTypedOutput.buildBoundary(this.boundary, this.isFirst, false);
            this.partHeader = MultipartTypedOutput.buildHeader(this.name, this.body);
            this.isBuilt = true;
        }
    }

    public MultipartTypedOutput() {
        this(UUID.randomUUID().toString());
    }

    MultipartTypedOutput(String str) {
        this.mimeParts = new LinkedList();
        this.boundary = str;
        byte[] bArrBuildBoundary = buildBoundary(str, false, true);
        this.footer = bArrBuildBoundary;
        this.length = bArrBuildBoundary.length;
    }

    List<byte[]> getParts() throws IOException {
        ArrayList arrayList = new ArrayList(this.mimeParts.size());
        for (MimePart mimePart : this.mimeParts) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mimePart.writeTo(byteArrayOutputStream);
            arrayList.add(byteArrayOutputStream.toByteArray());
        }
        return arrayList;
    }

    public void addPart(String str, TypedOutput typedOutput) {
        if (str == null) {
            throw new NullPointerException("Part name must not be null.");
        }
        if (typedOutput == null) {
            throw new NullPointerException("Part body must not be null.");
        }
        MimePart mimePart = new MimePart(str, typedOutput, this.boundary, this.mimeParts.isEmpty());
        this.mimeParts.add(mimePart);
        long size = mimePart.size();
        if (size == -1) {
            this.length = -1L;
            return;
        }
        long j = this.length;
        if (j != -1) {
            this.length = j + size;
        }
    }

    public int getPartCount() {
        return this.mimeParts.size();
    }

    @Override // retrofit.mime.TypedOutput
    public String mimeType() {
        return "multipart/form-data; boundary=" + this.boundary;
    }

    @Override // retrofit.mime.TypedOutput
    public long length() {
        return this.length;
    }

    @Override // retrofit.mime.TypedOutput
    public void writeTo(OutputStream outputStream) throws IOException {
        Iterator<MimePart> it = this.mimeParts.iterator();
        while (it.hasNext()) {
            it.next().writeTo(outputStream);
        }
        outputStream.write(this.footer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] buildBoundary(String str, boolean z, boolean z2) {
        try {
            StringBuilder sb = new StringBuilder();
            if (!z) {
                sb.append(VCardBuilder.VCARD_END_OF_LINE);
            }
            sb.append("--");
            sb.append(str);
            if (z2) {
                sb.append("--");
            }
            sb.append(VCardBuilder.VCARD_END_OF_LINE);
            return sb.toString().getBytes("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Unable to write multipart boundary", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] buildHeader(String str, TypedOutput typedOutput) {
        try {
            StringBuilder sb = new StringBuilder("Content-Disposition: form-data; name=\"");
            sb.append(str);
            if (typedOutput.fileName() != null) {
                sb.append("\"; filename=\"");
                sb.append(typedOutput.fileName());
            }
            sb.append("\"\r\nContent-Type: ");
            sb.append(typedOutput.mimeType());
            if (typedOutput.length() != -1) {
                sb.append("\r\nContent-Length: ").append(typedOutput.length());
            }
            sb.append("\r\nContent-Transfer-Encoding: binary\r\n\r\n");
            return sb.toString().getBytes("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Unable to write multipart header", e);
        }
    }
}
