package Interface;

import Domain.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Domain.Problem.delete_problem_fromBD;

public class EliminateFEN extends JPanel{
    private FrameProgram frame;
    private java.awt.List graphic_list;
    private SampleGame table;
    EliminateFEN(FrameProgram t) {
        frame = t;
        this.setLayout(new BorderLayout());
        List<String> problem = new ArrayList<String>();
        try {
            problem = Problem.load_problem_fromBD("BD_USERPROBLEMS");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        graphic_list = new java.awt.List();
        for (int i = 0; i < problem.size(); ++i) {
            graphic_list.add(problem.get(i));
        }
        graphic_list.setFont(new Font("Serif", Font.PLAIN, 15));
        graphic_list.setMultipleMode(false);
        graphic_list.addMouseListener(new double_click());
        JLabel label = new JLabel();
        label.setText("FEN + Player who start playing + Player who has to achive checkmate + number of plays");
        this.add(label, BorderLayout.BEFORE_FIRST_LINE);
        JPanel panelgraphiclist = new JPanel();
        panelgraphiclist.setSize(400, 400);
        panelgraphiclist.add(graphic_list);
        JButton Back = new JButton("Back");
        Back.setFont(new Font("serif", Font.PLAIN, 30));
        Back.addActionListener(new goBackListener());
        this.add(graphic_list, BorderLayout.CENTER);
        this.add(Back, BorderLayout.EAST);
        if (problem.get(0) != null) table = new SampleGame(problem.get(0));
        this.add(table,BorderLayout.SOUTH);
    }

    private class goBackListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            ModificatePanel Panel = new ModificatePanel(frame);
            frame.getMiFrame().setContentPane(Panel);
            frame.getMiFrame().revalidate();
            frame.getMiFrame().repaint();
        }
    }

    public java.awt.List getGraphic_list() {
        return graphic_list;
    }

    public SampleGame getTable() {
        return table;
    }

    public void setTable(SampleGame table) {
        this.table = table;
    }

    public class double_click extends MouseAdapter {
        public void mouseClicked(MouseEvent me) {
            if (me.getClickCount() == 2) {
                try {
                    delete_problem_fromBD(graphic_list.getSelectedItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(frame.getMiFrame(),
                        "Problem eliminated",
                        "",
                        JOptionPane.INFORMATION_MESSAGE);
                try {
                    if (Problem.load_problem_fromBD("BD_USERPROBLEMS").isEmpty()) {
                        Cover Panel = new Cover(frame);
                        frame.getMiFrame().setContentPane(Panel);
                        frame.getMiFrame().revalidate();
                        frame.getMiFrame().repaint();
                    }
                    else {
                        EliminateFEN panel = new EliminateFEN(frame);
                        frame.getMiFrame().getContentPane().removeAll();
                        frame.getMiFrame().setContentPane(panel);
                        frame.getMiFrame().revalidate();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                frame.getMiFrame().getContentPane().remove(table);
                table = new SampleGame(graphic_list.getSelectedItem());
                frame.getMiFrame().getContentPane().add(table,BorderLayout.SOUTH);
                frame.getMiFrame().revalidate();
                frame.getMiFrame().repaint();
            }
        }
    }
}