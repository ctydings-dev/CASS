/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.ui;

import CASS.data.invoice.InvoiceDTO;
import CASS.data.person.AccountDTO;

/**
 *
 * @author ctydi
 */
public class InvoiceTableModel extends ItemTableModel {

        public InvoiceTableModel(Object[] data, int size) {
            super(data);
            String[] columnNames = {"Invoice #", "Date"};
 this.setCols(columnNames);
        }

       


        public Object getValueAt(int row, int col) {

            if (col == 0) {
               return ((InvoiceDTO) this.getData()[row]).getName();
            }
  if (col == 1) { return ((InvoiceDTO) this.getData()[row]).getCreatedDate();
            }
          
  return ((AccountDTO) this.getData()[row]).getCreatedDate();
        }
    }

