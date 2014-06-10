package org.pentaho.reporting.sdk.designtime.gui;

import javax.swing.JComponent;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.pentaho.reporting.engine.classic.core.designtime.datafactory.editor.model.Query;
import org.pentaho.reporting.engine.classic.core.designtime.datafactory.editor.model.QueryDialogModel;
import org.pentaho.reporting.engine.classic.core.designtime.datafactory.editor.ui.QueryEditorPanel;
import org.pentaho.reporting.engine.classic.core.designtime.datafactory.editor.ui.TextFieldBinding;
import org.pentaho.reporting.libraries.base.util.ObjectUtilities;
import org.pentaho.reporting.libraries.base.util.StringUtils;

public class SampleDataSourceQueryEditorPanel extends QueryEditorPanel<String>
{
  private class QueryDocumentListener extends TextFieldBinding
  {
    protected void performUpdate()
    {
      QueryDialogModel<String> dialogModel = getDialogModel();
      final Query<String> selectedQuery = dialogModel.getSelectedQuery();
      if (selectedQuery == null)
      {
        return;
      }

      String text = queryTextArea.getText();
      if (StringUtils.isEmpty(text))
      {
        text = null;
      }
      if (ObjectUtilities.equal(text, selectedQuery.getQueryScript()))
      {
        return;
      }

      dialogModel.updateSelectedQuery(selectedQuery.updateQuery(text));
    }
  }

  private RSyntaxTextArea queryTextArea;

  public SampleDataSourceQueryEditorPanel(final QueryDialogModel<String> dialogModel)
  {
    super(dialogModel);
  }

  protected void initialize()
  {
    queryTextArea = new RSyntaxTextArea();
    queryTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
    queryTextArea.setEnabled(false);
    queryTextArea.getDocument().addDocumentListener(new QueryDocumentListener());
  }

  protected void updateSelectedQueryFromModel()
  {
    QueryDialogModel<String> dialogModel = getDialogModel();
    Query<String> selectedQuery = dialogModel.getSelectedQuery();
    if (selectedQuery == null)
    {
      queryTextArea.setEnabled(false);
      queryTextArea.setText(null);
    }
    else
    {
      queryTextArea.setEnabled(true);
      queryTextArea.setText(selectedQuery.getQuery());
    }
  }

  protected JComponent getQueryEditor()
  {
    return new RTextScrollPane(500, 300, queryTextArea, true);
  }
}
