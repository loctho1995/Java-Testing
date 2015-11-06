/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitap1_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author thinh_000
 */
public class Baitap1_1 extends javax.swing.JFrame {
    private boolean ktrathem = false;
    private boolean ktrasua = false;
    private boolean chedoCSDL = true;
    private DefaultTableModel modelTable;
    private String dbPort = "1434";
    private String dbConnection = "jdbc:sqlserver://localhost:1434;databaseName=Student;integratedSecurity=true;";
    private int rowTbleFile = 0;
    /**
     * Creates new form Baitap1_1
     */
    public Baitap1_1() {
        initComponents();
        _tfMaSinhVien.setEnabled(false);
        _tfTenSinhVien.setEnabled(false);
        _tfNgaySinh.setEnabled(false);
        _btHuy.setVisible(false);
        modelTable = new DefaultTableModel();
        modelTable.addColumn("Mã sinh viên");
        modelTable.addColumn("Tên sinh viên");
        modelTable.addColumn("Ngày sinh");
        _FileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        _tbleDanhSachSinhVien.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            _tfMaSinhVien.setText(_tbleDanhSachSinhVien.getValueAt(_tbleDanhSachSinhVien.getSelectedRow(), 0).toString());
            _tfTenSinhVien.setText(_tbleDanhSachSinhVien.getValueAt(_tbleDanhSachSinhVien.getSelectedRow(), 1).toString());
            _tfNgaySinh.setText(_tbleDanhSachSinhVien.getValueAt(_tbleDanhSachSinhVien.getSelectedRow(), 2).toString());
            }
        });
    }
              
    
    public void updateTableCSDL()
    {
        Connection cn;
        Statement st;
        ResultSet rs;
        int j = 0;
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(dbConnection);
            st = cn.createStatement();
            String sql = "select * from Student";
            rs = st.executeQuery(sql);
            
            while(rs.next())
            {
                if(modelTable.getRowCount() <= j)
                {
                    modelTable.addRow(new Object[]{});
                    _tbleDanhSachSinhVien.setModel(modelTable);
                }
                
                for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                   _tbleDanhSachSinhVien.setValueAt(rs.getString(i), j, i-1);
                }
                j++;
            }
            
            for(int k = j; k < _tbleDanhSachSinhVien.getRowCount(); k++)
                for(int l = 0; l < 3; l++)
                {
                    _tbleDanhSachSinhVien.setValueAt("", k, l);
                }
            rs.close();
            st.close();
            cn.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void updatenewDatabase(String ma, String ten, String ngaysinh)
    {
        Connection cn;
        Statement st;

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(dbConnection);
            st = cn.createStatement();
            String sql = "insert into Student values('" + ma + "' , '" + ten + "' , '" + ngaysinh + "')";
            st.executeUpdate(sql);
            st.close();
            cn.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void updateDatabase(String ma, String ten, String ngaysinh)
    {
        Connection cn;
        Statement st;

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(dbConnection);
            st = cn.createStatement();
            String sql = "update Student set Tensinhvien = '" + ten + "' , Ngaysinh = '" + ngaysinh + "' where mssv = '" + ma + "'";
            st.executeUpdate(sql);
            st.close();
            cn.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void deleteData(String ma)
    {
        Connection cn;
        Statement st;

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(dbConnection);
            st = cn.createStatement();
            String sql = "delete from student where mssv = '" + ma + "'";
            st.executeUpdate(sql);
            st.close();
            cn.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void updateTableFile(String ma, String ten, String ngaysinh)
    {
        if(modelTable.getRowCount() <= rowTbleFile)
        {
            modelTable.addRow(new Object[]{});
            _tbleDanhSachSinhVien.setModel(modelTable);
        }
        _tbleDanhSachSinhVien.setValueAt(ma, rowTbleFile, 0);
        _tbleDanhSachSinhVien.setValueAt(ten, rowTbleFile, 1);
        _tbleDanhSachSinhVien.setValueAt(ngaysinh, rowTbleFile, 2);
        rowTbleFile += 1;
    }
    
    public void deleteDataFile(int row)
    {
        for(int i = row; i < _tbleDanhSachSinhVien.getRowCount() - 1; i++)
        {
            _tbleDanhSachSinhVien.setValueAt(_tbleDanhSachSinhVien.getValueAt(row + 1, 0), row, 0);
            _tbleDanhSachSinhVien.setValueAt(_tbleDanhSachSinhVien.getValueAt(row + 1, 1), row, 1);
            _tbleDanhSachSinhVien.setValueAt(_tbleDanhSachSinhVien.getValueAt(row + 1, 2), row, 2);
        }
        
        _tbleDanhSachSinhVien.setValueAt("", rowTbleFile - 1, 0);
        _tbleDanhSachSinhVien.setValueAt("", rowTbleFile - 1, 1);
        _tbleDanhSachSinhVien.setValueAt("", rowTbleFile - 1, 2);
        
        rowTbleFile -= 1;
    }
    
    public void deleteModelTable()
    {
        for(int k = 0; k < _tbleDanhSachSinhVien.getRowCount(); k++)
                for(int l = 0; l < 3; l++)
                {
                    _tbleDanhSachSinhVien.setValueAt("", k, l);
                }
        rowTbleFile = 0;
    }
    
    public void updateTableFromFile(File file) throws FileNotFoundException, IOException
    {
        BufferedReader bR = new BufferedReader(new FileReader(file));
        deleteModelTable();
        String ma;
        while((ma = bR.readLine()) != null)
        {
            String ten = bR.readLine();
            String ngaysinh = bR.readLine();
            updateTableFile(ma, ten, ngaysinh);
        }
        bR.close();
        JOptionPane.showMessageDialog(null, "Mở file: " + file.getPath() + " thành công..!");
    }
    
    public void printtoFile(File file) throws IOException
    {
        String textnull = "";
        PrintWriter bW = new PrintWriter(new FileWriter(file));
        for(int i = 0; i < _tbleDanhSachSinhVien.getRowCount(); i++)
        {
            if(textnull.equals(_tbleDanhSachSinhVien.getValueAt(i, 0)))
            {
                
            }
            else
            {
                bW.println(_tbleDanhSachSinhVien.getValueAt(i, 0));
                bW.println(_tbleDanhSachSinhVien.getValueAt(i, 1));
                bW.println(_tbleDanhSachSinhVien.getValueAt(i, 2));
            }
        }
        bW.flush();
        bW.close();
        JOptionPane.showMessageDialog(null, "Ghi file: " + file.getPath() + " thành công..!");
    }
    
    public boolean ktraMssv(String mssv)
    {
        boolean ktra = true;
        for(int i = 0; i < _tbleDanhSachSinhVien.getRowCount(); i++)
        {
            if(mssv.equals(_tbleDanhSachSinhVien.getValueAt(i, 0)))
            {
                ktra = false;
                break;
            }
        }
        return ktra;
    }
    
    
    public boolean ktraNgaySinh(String ngaysinh)
    {
        if(ngaysinh.length() != 10)
            return false;
        
        int ngay, thang, nam;
        try
        {
            ngay = Integer.parseInt(ngaysinh.substring(3, 5));
            thang = Integer.parseInt(ngaysinh.substring(0, 2));
            nam = Integer.parseInt(ngaysinh.substring(6));
        }
        catch(Exception e)
        {
            return false;
        }
        
        ngay = Integer.parseInt(ngaysinh.substring(3, 5));
        thang = Integer.parseInt(ngaysinh.substring(0, 2));
        nam = Integer.parseInt(ngaysinh.substring(6));
        
        String text = "/";
        if(text.equals(ngaysinh.charAt(2)) || text.equals(ngaysinh.charAt(5)))
            return false;
        
        if(thang < 1 || thang > 12  || ngay < 1 || ngay > 30)
            return false;
        
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _FileChooser = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        _tfTenSinhVien = new javax.swing.JTextField();
        _tfNgaySinh = new javax.swing.JTextField();
        _tfMaSinhVien = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        _btLuu = new javax.swing.JButton();
        _btSua = new javax.swing.JButton();
        _btThem = new javax.swing.JButton();
        _btXoa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        _tbleDanhSachSinhVien = new javax.swing.JTable();
        _btMoFile = new javax.swing.JButton();
        _btLuuFile = new javax.swing.JButton();
        _btThoat = new javax.swing.JButton();
        _btHuy = new javax.swing.JButton();

        _FileChooser.setDialogTitle("");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Thông tin sinh viên");

        jLabel2.setText("Mã sinh viên:");

        jLabel3.setText("Tên sinh viên:");

        jLabel4.setText("Ngày sinh:");

        jLabel5.setText("(MM/dd/yyyy)");

        _btLuu.setText("Lưu");
        _btLuu.setEnabled(false);
        _btLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btLuuActionPerformed(evt);
            }
        });

        _btSua.setText("Sửa");
        _btSua.setEnabled(false);
        _btSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btSuaActionPerformed(evt);
            }
        });

        _btThem.setText("Thêm");
        _btThem.setEnabled(false);
        _btThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btThemActionPerformed(evt);
            }
        });

        _btXoa.setText("Xóa");
        _btXoa.setEnabled(false);
        _btXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(_tfMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(_tfTenSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(_tfNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(_btThem, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(_btLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(_btSua, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(_btXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_tfMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_tfTenSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_tfNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_btLuu, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(_btSua, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(_btThem, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(_btXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Danh sách sinh viên");

        _tbleDanhSachSinhVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sinh viên", "Tên sinh viên", "Ngày sinh"
            }
        ));
        jScrollPane1.setViewportView(_tbleDanhSachSinhVien);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        _btMoFile.setText("File");
        _btMoFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btMoFileActionPerformed(evt);
            }
        });

        _btLuuFile.setText("CSDL");
        _btLuuFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btLuuFileActionPerformed(evt);
            }
        });

        _btThoat.setText("Thoát");
        _btThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btThoatActionPerformed(evt);
            }
        });

        _btHuy.setText("Hủy");
        _btHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _btHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_btMoFile, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(_btLuuFile, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(_btHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(226, 226, 226)
                        .addComponent(_btThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_btMoFile, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_btLuuFile, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_btThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_btHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _btThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btThemActionPerformed
        if(!ktrathem)
        {
            _btLuu.setEnabled(true);
            _btSua.setEnabled(false);
            _btXoa.setEnabled(false);
            _tfMaSinhVien.setEnabled(true);
            _tfTenSinhVien.setEnabled(true);
            _tfNgaySinh.setEnabled(true);
            _btThem.setText("Hủy");
            ktrathem = true;
        }
        else
        {
            _btLuu.setEnabled(false);
            _btSua.setEnabled(true);
            _btXoa.setEnabled(true);
            _tfMaSinhVien.setEnabled(false);
            _tfTenSinhVien.setEnabled(false);
            _tfNgaySinh.setEnabled(false);
            _tfMaSinhVien.setText("");
            _tfTenSinhVien.setText("");
            _tfNgaySinh.setText("");
            _btThem.setText("Thêm");
            ktrathem = false;
        }
            
    }//GEN-LAST:event__btThemActionPerformed

    private void _btThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btThoatActionPerformed
        System.exit(0);
    }//GEN-LAST:event__btThoatActionPerformed

    private void _btLuuFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btLuuFileActionPerformed
        if(chedoCSDL)
        {
            _btThem.setEnabled(true);
            _btSua.setEnabled(true);
            _btXoa.setEnabled(true);
            deleteModelTable();
            updateTableCSDL();
        }
        else
        {
            if(_FileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                File file = new File(_FileChooser.getSelectedFile().getPath());
                if(!file.isFile())
                {
                    file = new File(_FileChooser.getSelectedFile().getPath() + ".txt");
                }
                    
                if(file.exists())
                {
                    if(JOptionPane.showConfirmDialog(this, "File: " + file.getPath() + " đã tồn tại.Bạn có muốn ghi đè lên File này ko ??...?") == JOptionPane.OK_OPTION)
                    {
                        try {
                            printtoFile(file);
                        } catch (IOException ex) {
                            Logger.getLogger(Baitap1_1.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                else
                {
                    try {
                        printtoFile(file);
                    } catch (IOException ex) {
                        Logger.getLogger(Baitap1_1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event__btLuuFileActionPerformed

    private void _btMoFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btMoFileActionPerformed
        if(chedoCSDL)
        {
            _btMoFile.setText("Mở File");
            _btLuuFile.setText("Lưu File");
            _btHuy.setVisible(true);
            _btThem.setEnabled(true);
            _btSua.setEnabled(true);
            _btXoa.setEnabled(true);
            chedoCSDL = false;
            deleteModelTable();
        }
        else
        {
            if(_FileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                File file = new File(_FileChooser.getSelectedFile().getPath());
                if(file.exists())
                {
                    try {
                        if(file.isFile())
                            updateTableFromFile(file);
                    } catch (IOException ex) {
                        Logger.getLogger(Baitap1_1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "File khong ton tai");
            }
        }
    }//GEN-LAST:event__btMoFileActionPerformed

    private void _btHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btHuyActionPerformed
        _btMoFile.setText("File");
        _btLuuFile.setText("CSDL");
        _btHuy.setVisible(false);
        chedoCSDL = true;
        _btThem.setEnabled(false);
        _btSua.setEnabled(false);
        _btXoa.setEnabled(false);
        deleteModelTable();
    }//GEN-LAST:event__btHuyActionPerformed

    private void _btSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btSuaActionPerformed
        String textnull = "";
        if(textnull.equals(_tfMaSinhVien.getText()))
        {
            JOptionPane.showMessageDialog(null, "Chọn sinh viên cần sửa");
        }
        else
        {
            _tfTenSinhVien.setEnabled(true);
            _tfNgaySinh.setEnabled(true);
            _btLuu.setEnabled(true);
            ktrasua = true;
            _btThem.setEnabled(false);
            _btSua.setEnabled(false);
        }
    }//GEN-LAST:event__btSuaActionPerformed

    private void _btLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btLuuActionPerformed
        String textnull = "";
        if(textnull.equals(_tfMaSinhVien.getText()) || textnull.equals(_tfTenSinhVien.getText()) || textnull.equals(_tfNgaySinh.getText()))
        {
            JOptionPane.showMessageDialog(null, "Mã sinh viên, Tên, Ngày sinh không được để trống\nVui lòng điền đầy đủ thông tin");
        }
        else
        {
            if(ktraMssv(_tfMaSinhVien.getText()) && ktraNgaySinh(_tfNgaySinh.getText().toString()))
                {
                if(chedoCSDL)
                {
                    if(!ktrasua)
                    {
                        updatenewDatabase(_tfMaSinhVien.getText(), _tfTenSinhVien.getText(), _tfNgaySinh.getText());
                        updateTableCSDL();
                        _btThem.setText("Thêm");
                        ktrathem = false;
                        _btLuu.setEnabled(false);
                        _btSua.setEnabled(true);
                        _btXoa.setEnabled(true);
                        _tfMaSinhVien.setEnabled(false);
                        _tfTenSinhVien.setEnabled(false);
                        _tfNgaySinh.setEnabled(false);
                        _tfMaSinhVien.setText("");
                        _tfTenSinhVien.setText("");
                        _tfNgaySinh.setText("");
                    }
                    else
                    {
                        updateDatabase(_tfMaSinhVien.getText(), _tfTenSinhVien.getText(), _tfNgaySinh.getText());
                        updateTableCSDL();
                        ktrasua = false;
                        _tfTenSinhVien.setEnabled(false);
                        _tfNgaySinh.setEnabled(false);
                        _btLuu.setEnabled(false);
                        _btXoa.setEnabled(true);
                        _btThem.setEnabled(true);
                        _tfMaSinhVien.setText("");
                        _tfTenSinhVien.setText("");
                        _tfNgaySinh.setText("");
                    }
                }
                else
                {
                    if(!ktrasua)
                    {
                        updateTableFile(_tfMaSinhVien.getText(), _tfTenSinhVien.getText(), _tfNgaySinh.getText());
                        //updateTableCSDL();

                        _btThem.setText("Thêm");
                        ktrathem = false;
                        _btLuu.setEnabled(false);
                        _btSua.setEnabled(true);
                        _btXoa.setEnabled(true);
                        _tfMaSinhVien.setEnabled(false);
                        _tfTenSinhVien.setEnabled(false);
                        _tfNgaySinh.setEnabled(false);
                        _tfMaSinhVien.setText("");
                        _tfTenSinhVien.setText("");
                        _tfNgaySinh.setText("");
                    }
                    else
                    {
                        //updateDatabase(_tfMaSinhVien.getText(), _tfTenSinhVien.getText(), _tfNgaySinh.getText());
                        _tbleDanhSachSinhVien.setValueAt(_tfTenSinhVien.getText(), _tbleDanhSachSinhVien.getSelectedRow(), 1);
                        _tbleDanhSachSinhVien.setValueAt(_tfNgaySinh.getText(), _tbleDanhSachSinhVien.getSelectedRow(), 2);
                        ktrasua = false;
                        _tfTenSinhVien.setEnabled(false);
                        _tfNgaySinh.setEnabled(false);
                        _btLuu.setEnabled(false);
                        _btXoa.setEnabled(true);
                        _btThem.setEnabled(true);
                        _tfMaSinhVien.setText("");
                        _tfTenSinhVien.setText("");
                        _tfNgaySinh.setText("");
                    }
                }
            }
            else
            {
                if(!ktraMssv(_tfMaSinhVien.getText()))
                    JOptionPane.showMessageDialog(null, "Mã sinh viên: " + _tfMaSinhVien.getText() + " này đã tồn tại, bạn vui lòng chọn Mã sinh viên khác");
                if(!ktraNgaySinh(_tfNgaySinh.getText()))
                    JOptionPane.showMessageDialog(null, "Ngày sinh không đúng định dạng!!!\nví dụ 09/29/1987");
            }
        }
    }//GEN-LAST:event__btLuuActionPerformed

    private void _btXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__btXoaActionPerformed
        String textnull = "";
        if(textnull.equals(_tfMaSinhVien.getText()))
        {
            JOptionPane.showMessageDialog(null, "Chọn sinh viên cần xóa");
        }
        else
        {
            if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa sinh viên: " + _tfMaSinhVien.getText() + " này chứ??") == JOptionPane.OK_OPTION)
            {
                if(chedoCSDL)
                {
                    deleteData(_tfMaSinhVien.getText());
                    updateTableCSDL();
                    _tfMaSinhVien.setText("");
                    _tfTenSinhVien.setText("");
                    _tfNgaySinh.setText("");
                }
                else
                {
                    deleteDataFile(_tbleDanhSachSinhVien.getSelectedRow());
                    _tfMaSinhVien.setText("");
                    _tfTenSinhVien.setText("");
                    _tfNgaySinh.setText("");
                }
            }
        }
    }//GEN-LAST:event__btXoaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Baitap1_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Baitap1_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Baitap1_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Baitap1_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Baitap1_1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser _FileChooser;
    private javax.swing.JButton _btHuy;
    private javax.swing.JButton _btLuu;
    private javax.swing.JButton _btLuuFile;
    private javax.swing.JButton _btMoFile;
    private javax.swing.JButton _btSua;
    private javax.swing.JButton _btThem;
    private javax.swing.JButton _btThoat;
    private javax.swing.JButton _btXoa;
    private javax.swing.JTable _tbleDanhSachSinhVien;
    private javax.swing.JTextField _tfMaSinhVien;
    private javax.swing.JTextField _tfNgaySinh;
    private javax.swing.JTextField _tfTenSinhVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
