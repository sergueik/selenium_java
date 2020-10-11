package example;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import example.AutoExcel;
import example.DataDirection;
import example.ExcelSetting;
import example.model.Product;
import example.parameters.DirectExportPara;
import example.parameters.FieldSetting;
import example.parameters.ImportPara;
import example.parameters.TemplateExportPara;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoExcelTest {
	private static String resourcePath = null;

	@Before
	public void before() {
		resourcePath = this.getClass().getResource("/").getPath();
	}

	@Test
	public void test1_exportWithTemplate() throws Exception {
		List<TemplateExportPara> paras = new ArrayList<>();

		paras.add(new TemplateExportPara("BusinessUnit", DataGenerator.genBusinessUnit()));
		paras.add(new TemplateExportPara("Contract", DataGenerator.genContracts()));
		paras.add(new TemplateExportPara("Project", DataGenerator.genProjects()));

		List<Product> products = DataGenerator.genProducts();
		TemplateExportPara para3 = new TemplateExportPara("Product", products);
		para3.setInserted(true);
		paras.add(para3);

		TemplateExportPara para5 = new TemplateExportPara("Product2", products);
		para5.setDataDirection(DataDirection.Right);
		paras.add(para5);

		ExcelSetting excelSetting = new ExcelSetting();
		excelSetting.setRemovedSheets(Arrays.asList("will be removed"));

		AutoExcel.save(this.getClass().getResource("/template/Common.xlsx").getPath(),
				resourcePath + "ExportWithTemplate.xlsx", paras, excelSetting);
	}

	@Test
	public void test2_exportDirectly() throws Exception {
		String outputFile = resourcePath + "Export.xlsx";
		List<DirectExportPara> paras = new ArrayList<>();
		paras.add(
				new DirectExportPara(DataGenerator.genProjects(), "Projects", DataGenerator.genProjectFieldSettings()));
		paras.add(new DirectExportPara(DataGenerator.genContracts()));
		AutoExcel.save(outputFile, paras);
	}

	@Test
	public void test3_importExcel() throws Exception {
		@SuppressWarnings("serial")
		List<ImportPara> importParas = new ArrayList<ImportPara>() {
			{
				add(new ImportPara("BusinessUnit"));
				add(new ImportPara("Contract", DataDirection.Down));
				add(new ImportPara("Project", DataDirection.Down));
//            add(new ImportPara("Product", DataDirection.Down));   not supported now
			}
		};
		// alternatively,
		importParas = Arrays.asList(new ImportPara[] { new ImportPara("BusinessUnit"),
				new ImportPara("Contract", DataDirection.Down), new ImportPara("Project", DataDirection.Down) });
		String inputFile = resourcePath + "ExportWithTemplate.xlsx";
		Map<String, List<Map<String, Object>>> datas = AutoExcel.read(inputFile, importParas);
	}
}
